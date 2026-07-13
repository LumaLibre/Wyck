package dev.wyck.codegen;

import net.minecraft.SharedConstants;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Bootstrap;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ReferenceGenerator {

    // matches: @AsOf("1.2.3") \n public static final SomeType FIELD_NAME =
    private static final Pattern EXISTING_FIELD = Pattern.compile(
        "@AsOf\\(\"([^\"]+)\"\\)\\s+public\\s+static\\s+final\\s+\\w+\\s+(\\w+)\\s*="
    );

    // matches: methods and constructors
    //   @AsOf("1.2.3") \n public ReturnType<Generic> methodName(
    //   @AsOf("1.2.3") \n ClassName()
    private static final Pattern EXISTING_MEMBER = Pattern.compile(
        "@AsOf\\(\"([^\"]+)\"\\)\\s+(?:public\\s+)?(?:[\\w.<>]+\\s+)?(\\w+)\\s*\\("
    );

    static void main(String[] args) throws Exception {
        String outputRoot = args[0];
        String version = args[1];

        // necessary inits
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();

        for (GeneratorSpec spec : Generators.ALL) {
            Path outputPath = Path.of(
                outputRoot,
                spec.outputPackage().replace('.', '/'),
                spec.outputClass() + ".java"
            );

            Map<String, String> existingVersions = readExistingVersions(outputPath);
            List<String> preservedConstants = readPreservedConstants(outputPath);

            String source = switch (spec) {
                case ReferenceSpec ref -> generateReference(ref, version, existingVersions);
                case EnumSpec enumSpec -> generateEnum(enumSpec, version, existingVersions, preservedConstants);
                case ConstantSpec constantSpec -> generateConstant(constantSpec, version, existingVersions, preservedConstants);
            };

            Files.createDirectories(outputPath.getParent());
            Files.writeString(outputPath, source);
            System.out.println("generated " + outputPath);
        }
    }

    private static Map<String, String> readExistingVersions(Path outputPath) throws Exception {
        Map<String, String> versions = new HashMap<>();
        if (!Files.exists(outputPath)) {
            return versions;
        }

        String existing = Files.readString(outputPath);
        for (Pattern pattern : List.of(EXISTING_FIELD, EXISTING_MEMBER)) {
            Matcher matcher = pattern.matcher(existing);
            while (matcher.find()) {
                String memberVersion = matcher.group(1);
                String memberName = matcher.group(2);
                versions.putIfAbsent(memberName, memberVersion);
            }
        }

        return versions;
    }

    /**
     * Finds hand-maintained enum constants in an existing generated file.
     * The generator never emits annotations on constants, so any constant
     * entry carrying an annotation (e.g. {@code @Deprecated}) is treated as
     * manually added and is carried over verbatim on regeneration.
     */
    private static List<String> readPreservedConstants(Path outputPath) throws Exception {
        if (!Files.exists(outputPath)) {
            return List.of();
        }

        String existing = Files.readString(outputPath);
        int enumIndex = existing.indexOf("public enum ");
        if (enumIndex < 0) {
            return List.of();
        }
        int brace = existing.indexOf('{', enumIndex);
        if (brace < 0) {
            return List.of();
        }

        // split the constant section (up to the first top-level ';') on
        // top-level commas, ignoring commas inside parens, strings, comments
        List<String> entries = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        int depth = 0;
        boolean inString = false;
        boolean inBlockComment = false;
        boolean inLineComment = false;

        outer:
        for (int i = brace + 1; i < existing.length(); i++) {
            char c = existing.charAt(i);
            char next = i + 1 < existing.length() ? existing.charAt(i + 1) : '\0';

            if (inString) {
                current.append(c);
                if (c == '"' && existing.charAt(i - 1) != '\\') {
                    inString = false;
                }
                continue;
            }
            if (inBlockComment) {
                current.append(c);
                if (c == '*' && next == '/') {
                    current.append(next);
                    i++;
                    inBlockComment = false;
                }
                continue;
            }
            if (inLineComment) {
                current.append(c);
                if (c == '\n') {
                    inLineComment = false;
                }
                continue;
            }

            switch (c) {
                case '"' -> {
                    inString = true;
                    current.append(c);
                }
                case '/' -> {
                    if (next == '*') {
                        inBlockComment = true;
                    } else if (next == '/') {
                        inLineComment = true;
                    }
                    current.append(c);
                }
                case '(' -> {
                    depth++;
                    current.append(c);
                }
                case ')' -> {
                    depth--;
                    current.append(c);
                }
                case ',' -> {
                    if (depth == 0) {
                        entries.add(current.toString());
                        current.setLength(0);
                    } else {
                        current.append(c);
                    }
                }
                case ';' -> {
                    if (depth == 0) {
                        entries.add(current.toString());
                        break outer;
                    }
                    current.append(c);
                }
                default -> current.append(c);
            }
        }

        List<String> preserved = new ArrayList<>();
        for (String entry : entries) {
            String trimmed = entry.strip();
            if (trimmed.isEmpty()) {
                continue;
            }
            boolean annotated = trimmed.lines()
                .map(String::strip)
                .anyMatch(line -> line.startsWith("@"));
            if (annotated) {
                preserved.add(trimmed);
            }
        }
        return preserved;
    }

    private static @Nullable String preservedConstantName(String entry) {
        for (String line : entry.lines().toList()) {
            String s = line.strip();
            if (s.isEmpty() || s.startsWith("/") || s.startsWith("*")) {
                continue;
            }
            // strip any leading annotations sharing the line with the constant
            s = s.replaceAll("^(?:@[\\w.]+(?:\\([^)]*\\))?\\s*)+", "");
            if (s.isEmpty()) {
                continue;
            }
            Matcher matcher = Pattern.compile("^(\\w+)").matcher(s);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    /**
     * Appends preserved manual constants (re-indented) after the reflected
     * ones, skipping any whose name the current generation already emitted.
     */
    private static void appendConstants(StringBuilder sb, List<String> generated, List<String> preservedConstants, Set<String> emittedNames) {
        List<String> blocks = new ArrayList<>(generated);

        for (String entry : preservedConstants) {
            String name = preservedConstantName(entry);
            if (name == null) {
                System.err.println("warning: could not parse preserved constant entry, dropping:\n" + entry);
                continue;
            }
            if (!emittedNames.add(name)) {
                System.err.println("warning: manual constant " + name + " now exists in vanilla again, dropping the manual copy");
                continue;
            }
            blocks.add(entry.lines()
                .map(line -> {
                    String stripped = line.strip();
                    if (stripped.isEmpty()) {
                        return "";
                    }
                    // javadoc continuation lines get one extra space so the asterisks align
                    return (stripped.startsWith("*") ? "     " : "    ") + stripped;
                })
                .reduce((a, b) -> a + "\n" + b)
                .orElse(entry));
        }

        for (int i = 0; i < blocks.size(); i++) {
            sb.append(blocks.get(i))
                .append(i == blocks.size() - 1 ? ";" : ",")
                .append("\n");
        }
    }

    private static void appendHeader(StringBuilder sb, GeneratorSpec spec, String version) {
        List<String> prose = new ArrayList<>();
        List<String> tags = new ArrayList<>();
        boolean inTags = false;
        for (String line : spec.javadoc().lines().toList()) {
            if (line.startsWith("@")) {
                inTags = true;
            }
            (inTags ? tags : prose).add(line);
        }
        // drop blank separator lines left dangling at the end of the prose
        while (!prose.isEmpty() && prose.getLast().isBlank()) {
            prose.removeLast();
        }

        sb.append("/**\n");
        sb.append(" * Auto-generated. Do not modify!\n");
        sb.append(" * Run ./gradlew generateSources to regenerate.\n");
        sb.append(" * <p>\n");
        for (String line : prose) {
            sb.append(line.isBlank() ? " *" : " * " + line).append("\n");
        }
        sb.append(" * </p>\n");
        for (String line : tags) {
            sb.append(" * ").append(line).append("\n");
        }
        sb.append(" *\n");
        sb.append(" * @since ").append(spec.since()).append("\n");
        sb.append(" * @version ").append(version).append("\n");
        sb.append(" * @author Wyck codegen\n");
        sb.append(" */\n");
        sb.append("@NullMarked\n");
        sb.append("@AsOf(\"").append(spec.since()).append("\")\n");
        sb.append("@Generated(\"").append(Instant.now().toString()).append("\")\n");
    }

    private static String generateReference(ReferenceSpec spec, String version, Map<String, String> existingVersions) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();

        sb.append("package ").append(spec.outputPackage()).append(";\n\n");
        sb.append("import dev.wyck.annotations.AsOf;\n");
        sb.append("import dev.wyck.annotations.Generated;\n");
        sb.append("import dev.wyck.keys.ResourceKey;\n");
        sb.append("import org.jspecify.annotations.NullMarked;\n\n");
        appendHeader(sb, spec, version);
        sb.append("public final class ").append(spec.outputClass()).append(" {\n\n");

        // guards against duplicate field names across source classes
        Set<String> emittedNames = new HashSet<>();

        for (Class<?> sourceClass : spec.sourceClasses()) {
            sb.append("    // From: ").append(sourceClass.getSimpleName()).append(" \n");

            for (Field field : sourceClass.getDeclaredFields()) {
                // only static fields
                if (!Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                if (spec.fieldFilter() != null) {
                    if (!spec.fieldFilter().test(field)) {
                        continue;
                    }
                } else if (!spec.registryType().isAssignableFrom(field.getType())) {
                    continue;
                }

                field.setAccessible(true);
                Object entry = field.get(null);

                Identifier key = spec.registryLookup().apply(entry);
                if (key == null) {
                    System.err.println("warning: no registry key for " + field.getName() + ", skipping");
                    continue;
                }

                if (!emittedNames.add(field.getName())) {
                    System.err.println("warning: duplicate field name " + field.getName() + " in " + sourceClass.getSimpleName() + ", skipping");
                    continue;
                }

                // existing fields keep their original @AsOf; new fields get the current version
                String fieldVersion = existingVersions.getOrDefault(field.getName(), spec.since());

                sb.append("    @AsOf(\"").append(fieldVersion).append("\")\n");
                sb.append("    public static final ").append(spec.typeSimpleName()).append(" ")
                    .append(field.getName())
                    .append(" = reference(\"")
                    .append(key.getPath())
                    .append("\");\n");
            }

            sb.append("\n");
        }

        sb.append("    private static ").append(spec.typeSimpleName())
            .append(" reference(String path) {\n");

        if (spec.keyChain() == null) {
            sb.append("        return ").append(spec.referenceCall()).append("(ResourceKey.minecraft(path));\n");
        } else {
            sb.append("        ").append(spec.typeSimpleName()).append(" keyed = ")
                .append(spec.referenceCall()).append("(ResourceKey.minecraft(path));\n");
            sb.append("        dev.wyck.keys.KeyChains.").append(spec.keyChain()).append(".append(keyed);\n");
            sb.append("        return keyed;\n");
        }

        sb.append("    }\n\n");
        sb.append("    private ").append(spec.outputClass()).append("() {\n");
        sb.append("        throw new UnsupportedOperationException(\"Not intended for instantiation\");\n");
        sb.append("    }\n");
        sb.append("}\n");

        return sb.toString();
    }

    private static String generateEnum(EnumSpec spec, String version, Map<String, String> existingVersions, List<String> preservedConstants) {
        Enum<?>[] constants = spec.sourceEnum().getEnumConstants();
        String outputClass = spec.outputClass();

        StringBuilder sb = new StringBuilder();

        sb.append("package ").append(spec.outputPackage()).append(";\n\n");
        sb.append("import dev.wyck.annotations.AsOf;\n");
        sb.append("import dev.wyck.annotations.Generated;\n");
        sb.append("import dev.wyck.wrapper.internal.KeyedEnumTranslator;\n");
        sb.append("import dev.wyck.wrapper.internal.WrappedEnumerator;\n");
        sb.append("import org.jspecify.annotations.NullMarked;\n\n");
        appendHeader(sb, spec, version);
        sb.append("public enum ").append(outputClass)
            .append(" implements WrappedEnumerator<").append(outputClass).append("> {\n\n");

        List<String> generated = new ArrayList<>();
        Set<String> emittedNames = new HashSet<>();
        for (Enum<?> constant : constants) {
            String key = spec.keyExtractor().apply(constant);
            generated.add("    " + constant.name() + "(\"" + key + "\")");
            emittedNames.add(constant.name());
        }
        appendConstants(sb, generated, preservedConstants, emittedNames);

        sb.append("\n");
        sb.append("    public static final KeyedEnumTranslator<").append(outputClass)
            .append("> TRANSLATOR = KeyedEnumTranslator.byKey(")
            .append(outputClass).append("::getKey, ")
            .append(outputClass).append(".values());\n\n");

        sb.append("    private final String key;\n\n");

        sb.append("    @AsOf(\"").append(memberVersion(existingVersions, outputClass, spec)).append("\")\n");
        sb.append("    ").append(outputClass).append("(String key) {\n");
        sb.append("        this.key = key;\n");
        sb.append("    }\n\n");

        sb.append("    /**\n");
        sb.append("     * The vanilla name for this ").append(spec.sourceEnum().getSimpleName()).append(" value.\n");
        sb.append("     * @return the vanilla key for this enum value\n");
        sb.append("     * @since ").append(memberVersion(existingVersions, "getKey", spec)).append("\n");
        sb.append("     */\n");
        sb.append("    @AsOf(\"").append(memberVersion(existingVersions, "getKey", spec)).append("\")\n");
        sb.append("    public String getKey() {\n");
        sb.append("        return this.key;\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    @AsOf(\"").append(memberVersion(existingVersions, "translator", spec)).append("\")\n");
        sb.append("    public KeyedEnumTranslator<").append(outputClass).append("> translator() {\n");
        sb.append("        return TRANSLATOR;\n");
        sb.append("    }\n");
        sb.append("}\n");

        return sb.toString();
    }

    private static String generateConstant(ConstantSpec spec, String version, Map<String, String> existingVersions, List<String> preservedConstants) throws IllegalAccessException {
        String outputClass = spec.outputClass();
        String typeName = spec.registryType().getSimpleName().toLowerCase();

        StringBuilder sb = new StringBuilder();

        sb.append("package ").append(spec.outputPackage()).append(";\n\n");
        sb.append("import dev.wyck.annotations.AsOf;\n");
        sb.append("import dev.wyck.annotations.Generated;\n");
        sb.append("import dev.wyck.keys.ResourceKey;\n");
        sb.append("import dev.wyck.registry.internal.RegistryId;\n");
        sb.append("import dev.wyck.wrapper.internal.RegisteredConstantTranslator;\n");
        sb.append("import dev.wyck.wrapper.internal.WrappedConstant;\n");
        sb.append("import org.jspecify.annotations.NullMarked;\n\n");
        appendHeader(sb, spec, version);
        sb.append("public enum ").append(outputClass)
            .append(" implements WrappedConstant<").append(outputClass).append("> {\n\n");

        List<String> generated = new ArrayList<>();
        Set<String> emittedNames = new HashSet<>();

        for (Class<?> sourceClass : spec.sourceClasses()) {
            for (Field field : sourceClass.getDeclaredFields()) {
                // only static fields
                if (!Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                if (spec.fieldFilter() != null) {
                    if (!spec.fieldFilter().test(field)) {
                        continue;
                    }
                } else if (!spec.registryType().isAssignableFrom(field.getType())) {
                    continue;
                }

                field.setAccessible(true);
                Object entry = field.get(null);

                Identifier key = spec.registryLookup().apply(entry);
                if (key == null) {
                    System.err.println("warning: no registry key for " + field.getName() + ", skipping");
                    continue;
                }

                if (!emittedNames.add(field.getName())) {
                    System.err.println("warning: duplicate field name " + field.getName() + " in " + sourceClass.getSimpleName() + ", skipping");
                    continue;
                }

                generated.add("    " + field.getName() + "(\"" + key.getPath() + "\")");
            }
        }

        appendConstants(sb, generated, preservedConstants, emittedNames);

        sb.append("\n");
        sb.append("    public static final RegisteredConstantTranslator<").append(outputClass)
            .append("> TRANSLATOR = RegisteredConstantTranslator.of(RegistryId.").append(spec.registryId())
            .append(", ").append(outputClass).append("::resourceKey, ")
            .append(outputClass).append(".values());\n");

        sb.append("    private final String key;\n\n");

        sb.append("    @AsOf(\"").append(memberVersion(existingVersions, outputClass, spec)).append("\")\n");
        sb.append("    ").append(outputClass).append("(String key) {\n");
        sb.append("        this.key = key;\n");
        sb.append("    }\n\n");

        sb.append("    /**\n");
        sb.append("     * The vanilla registry path for this ").append(typeName).append(".\n");
        sb.append("     * @return the registry path for this ").append(typeName).append("\n");
        sb.append("     * @since ").append(memberVersion(existingVersions, "key", spec)).append("\n");
        sb.append("     */\n");
        sb.append("    @AsOf(\"").append(memberVersion(existingVersions, "key", spec)).append("\")\n");
        sb.append("    public String key() {\n");
        sb.append("        return this.key;\n");
        sb.append("    }\n\n");

        sb.append("    @AsOf(\"").append(memberVersion(existingVersions, "resourceKey", spec)).append("\")\n");
        sb.append("    public ResourceKey resourceKey() {\n");
        sb.append("        return ResourceKey.minecraft(this.key);\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    @AsOf(\"").append(memberVersion(existingVersions, "translator", spec)).append("\")\n");
        sb.append("    public RegisteredConstantTranslator<").append(outputClass).append("> translator() {\n");
        sb.append("        return TRANSLATOR;\n");
        sb.append("    }\n");
        sb.append("}\n");

        return sb.toString();
    }

    // existing members keep their original @AsOf; new members get the spec's since version
    private static String memberVersion(Map<String, String> existingVersions, String memberName, GeneratorSpec spec) {
        return existingVersions.getOrDefault(memberName, spec.since());
    }
}