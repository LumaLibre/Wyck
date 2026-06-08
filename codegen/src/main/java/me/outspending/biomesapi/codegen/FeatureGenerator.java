package me.outspending.biomesapi.codegen;

import net.minecraft.SharedConstants;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Bootstrap;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// AsOf 2.3.0
public final class FeatureGenerator {

    private static final String SINCE = "2.3.0";
    // matches: @AsOf("1.2.3") \n public static final SomeType FIELD_NAME =
    private static final Pattern EXISTING_FIELD = Pattern.compile(
        "@AsOf\\(\"([^\"]+)\"\\)\\s+public\\s+static\\s+final\\s+\\w+\\s+(\\w+)\\s*="
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

            String source = generate(spec, version, existingVersions);
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
        Matcher matcher = EXISTING_FIELD.matcher(existing);
        while (matcher.find()) {
            String fieldVersion = matcher.group(1);
            String fieldName = matcher.group(2);
            versions.put(fieldName, fieldVersion);
        }

        return versions;
    }

    private static String generate(GeneratorSpec spec, String version, Map<String, String> existingVersions) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();

        sb.append("package ").append(spec.outputPackage()).append(";\n\n");
        sb.append("import me.outspending.biomesapi.annotations.AsOf;\n");
        sb.append("import me.outspending.biomesapi.annotations.Generated;\n");
        sb.append("import me.outspending.biomesapi.registry.BiomeResourceKey;\n");
        sb.append("import org.jspecify.annotations.NullMarked;\n\n");
        sb.append("/**\n");
        sb.append(" * Auto-generated. Do not modify!\n");
        sb.append(" * Run ./gradlew generateSources to regenerate.\n");
        sb.append(" * <p>\n");
        sb.append(" * ").append(spec.javadoc()).append("\n");
        sb.append(" * </p>\n");
        sb.append(" *\n");
        sb.append(" * @since ").append(SINCE).append("\n");
        sb.append(" * @version ").append(version).append("\n");
        sb.append(" * @author BiomesAPI codegen\n");
        sb.append(" */\n");
        sb.append("@NullMarked\n");
        sb.append("@AsOf(\"").append(version).append("\")\n");
        sb.append("@Generated(\"").append(Instant.now().toString()).append("\")\n");
        sb.append("public final class ").append(spec.outputClass()).append(" {\n\n");

        // guards against duplicate field names across source classes
        Set<String> emittedNames = new HashSet<>();

        for (Class<?> sourceClass : spec.sourceClasses()) {
            sb.append("    // From: ").append(sourceClass.getSimpleName()).append(" \n");

            for (Field field : sourceClass.getDeclaredFields()) {
                // only static fields of the matched type
                if (!Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                if (!spec.registryType().isAssignableFrom(field.getType())) {
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
                String fieldVersion = existingVersions.getOrDefault(field.getName(), version);

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
        sb.append("        return ").append(spec.referenceCall()).append("(BiomeResourceKey.minecraft(path));\n");
        sb.append("    }\n\n");
        sb.append("    private ").append(spec.outputClass()).append("() {\n");
        sb.append("        throw new UnsupportedOperationException(\"Not intended for instantiation\");\n");
        sb.append("    }\n");
        sb.append("}\n");

        return sb.toString();
    }
}