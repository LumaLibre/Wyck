package dev.wyck.codegen;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.ParameterizedTypeName;
import com.palantir.javapoet.TypeSpec;
import net.minecraft.SharedConstants;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Bootstrap;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
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
import java.util.stream.Collectors;

public final class ReferenceGenerator {

    // matches: @AsOf("1.2.3") \n public static final SomeType FIELD_NAME =
    // (modifiers are optional: interface constants are emitted without them)
    private static final Pattern EXISTING_FIELD = Pattern.compile(
            "@AsOf\\(\"([^\"]+)\"\\)\\s+(?:public\\s+static\\s+final\\s+)?\\w+\\s+(\\w+)\\s*="
    );

    // matches: methods and constructors
    //   @AsOf("1.2.3") \n public ReturnType<Generic> methodName(
    //   @AsOf("1.2.3") \n ClassName()
    private static final Pattern EXISTING_MEMBER = Pattern.compile(
            "@AsOf\\(\"([^\"]+)\"\\)\\s+(?:public\\s+)?(?:[\\w.<>]+\\s+)?(\\w+)\\s*\\("
    );
    private static final Pattern AS_OF_RE = Pattern.compile("@AsOf\\((.*)\\)");

    static void main(String[] args) throws Exception {
        String outputRoot = args[0];
        String version = args[1];
        Path outputRootPath = Path.of(outputRoot);
        try {
            // necessary inits
            SharedConstants.tryDetectVersion();
            Bootstrap.bootStrap();

            for (GeneratorSpec spec : Generators.ALL) {
                Path outputPath = Path.of(
                        outputRoot,
                        spec.outputClass().packageName().replace('.', '/'),
                        spec.outputClass().simpleName() + ".java"
                );

                Map<String, String> existingVersions = readExistingVersions(outputPath);
                List<String> preservedConstants = readPreservedConstants(outputPath);

                TypeSpec typeSpec = createTypeSpec(spec, version, existingVersions, preservedConstants);
                JavaFile javaFile = JavaFile.builder(spec.outputClass().packageName(), typeSpec)
                        .indent("    ")
                        .build();
                javaFile.writeTo(outputRootPath);
                System.out.println("generated " + outputPath);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.exit(1);
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

    private static TypeSpec createTypeSpec(GeneratorSpec generatorSpec, String version, Map<String, String> existingVersions, List<String> preservedConstants) throws IllegalAccessException {
        TypeSpec.Builder typeSpec;
        if (generatorSpec instanceof ReferenceSpec referenceSpec) {
            if (referenceSpec.asInterface()) {
                typeSpec = TypeSpec.interfaceBuilder(referenceSpec.outputClass())
                        .addModifiers(javax.lang.model.element.Modifier.PUBLIC)
                        .addAnnotation(ApiStatus.NonExtendable.class);
            } else {
                typeSpec = TypeSpec.classBuilder(referenceSpec.outputClass())
                        .addModifiers(javax.lang.model.element.Modifier.FINAL, javax.lang.model.element.Modifier.PUBLIC)
                        .addMethod(MethodSpec.constructorBuilder()
                                .addCode("throw new $T(\"Not intended for instantiation\");", UnsupportedOperationException.class)
                                .build()
                        );
            }
        } else {
            typeSpec = TypeSpec.enumBuilder(generatorSpec.outputClass());
            if (generatorSpec instanceof ConstantSpec constantSpec) {
                typeSpec.addSuperinterface(ParameterizedTypeName.get(
                        ClassName.get("dev.wyck.wrapper", "WrappedConstant"),
                        constantSpec.outputClass()
                ));
            } else if (generatorSpec instanceof EnumSpec constantSpec) {
                typeSpec.addSuperinterface(ParameterizedTypeName.get(
                        ClassName.get("dev.wyck.wrapper", "WrappedEnumerator"),
                        constantSpec.outputClass()
                ));
            }
        }
        typeSpec.addModifiers(javax.lang.model.element.Modifier.PUBLIC);
        String header = """
                Auto-generated. Do not modify!
                Run ./gradlew generateSources to regenerate.
                <p>
                %s
                </p>
                
                %s
                @since %s
                @version %s
                @author Wyck codegen
                """.formatted(
                generatorSpec.javadoc().lines()
                        .filter(line -> !line.startsWith("@") && !line.isEmpty())
                        .collect(Collectors.joining("\n")),
                generatorSpec.javadoc().lines()
                        .filter(line -> line.startsWith("@"))
                        .collect(Collectors.joining("\n")),
                generatorSpec.since(),
                version
        );
        typeSpec.addJavadoc(header);
        typeSpec.addAnnotation(AnnotationSpec.builder(NullMarked.class).build());
        typeSpec.addAnnotation(AnnotationSpec.builder(ClassName.get("dev.wyck.annotations", "AsOf"))
                .addMember("value", "$S", generatorSpec.since())
                .build()
        );
        typeSpec.addAnnotation(AnnotationSpec.builder(ClassName.get("dev.wyck.annotations", "Generated"))
                .addMember("value", "$S", Instant.now().toString())
                .build()
        );
        switch (generatorSpec) {
            case ConstantSpec constantSpec ->
                    appendConstantFields(constantSpec, typeSpec, preservedConstants, existingVersions);
            case EnumSpec enumSpec -> appendEnumFields(enumSpec, typeSpec, preservedConstants, existingVersions);
            case ReferenceSpec referenceSpec -> appendReferenceFields(referenceSpec, typeSpec, existingVersions);
        }
        return typeSpec.build();
    }

    private static void appendPreservedEnumConstants(TypeSpec.Builder typeSpec, List<String> preservedConstants, Set<String> emittedNames) {
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
            Matcher asOfMatcher = AS_OF_RE.matcher(entry);
            if (!asOfMatcher.find()) {
                continue;
            }
            String since = asOfMatcher.group(1);
            Matcher parameterMatcher = Pattern.compile("%s\\((.*)\\)".formatted(name)).matcher(entry);
            if (!parameterMatcher.find()) {
                typeSpec.addEnumConstant(name, TypeSpec.anonymousClassBuilder(
                                ""
                        ).addAnnotation(AnnotationSpec.builder(ClassName.get("dev.wyck.annotations", "AsOf"))
                                .addMember("value", "$L", since)
                                .build()
                        ).build()
                );
            } else {
                typeSpec.addEnumConstant(name, TypeSpec.anonymousClassBuilder(parameterMatcher.group(1))
                        .addAnnotation(AnnotationSpec.builder(ClassName.get("dev.wyck.annotations", "AsOf"))
                                .addMember("value", "$L", since)
                                .build()
                        ).build()
                );
            }
        }
    }

    private static void appendEnumConstructor(TypeSpec.Builder typeSpec, GeneratorSpec generatorSpec, Map<String, String> existingVersions) {
        typeSpec.addField(FieldSpec.builder(String.class, "key", javax.lang.model.element.Modifier.PRIVATE, javax.lang.model.element.Modifier.FINAL)
                .build()
        );
        typeSpec.addMethod(MethodSpec.constructorBuilder()
                .addAnnotation(
                        AnnotationSpec.builder(ClassName.get("dev.wyck.annotations", "AsOf"))
                                .addMember("value", "$S", memberVersion(existingVersions, generatorSpec.outputClass().simpleName(), generatorSpec))
                                .build()
                )
                .addParameter(String.class, "key")
                .addCode("this.key = key;")
                .build()
        );
    }

    private static void appendConstantFields(ConstantSpec constantSpec, TypeSpec.Builder typeSpec, List<String> preservedConstants, Map<String, String> existingVersions) throws IllegalAccessException {
        Set<String> emittedNames = new HashSet<>();

        for (Class<?> sourceClass : constantSpec.sourceClasses()) {
            for (Field field : sourceClass.getDeclaredFields()) {
                // only static fields
                if (!Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                if (constantSpec.fieldFilter() != null) {
                    if (!constantSpec.fieldFilter().test(field)) {
                        continue;
                    }
                } else if (!constantSpec.registryType().isAssignableFrom(field.getType())) {
                    continue;
                }

                field.setAccessible(true);
                Object entry = field.get(null);

                Identifier key = constantSpec.registryLookup().apply(entry);
                if (key == null) {
                    System.err.println("warning: no registry key for " + field.getName() + ", skipping");
                    continue;
                }

                if (!emittedNames.add(field.getName())) {
                    System.err.println("warning: duplicate field name " + field.getName() + " in " + sourceClass.getSimpleName() + ", skipping");
                    continue;
                }

                typeSpec.addEnumConstant(field.getName(), TypeSpec.anonymousClassBuilder("$S", key.getPath())
                        .build());
            }
        }
        appendPreservedEnumConstants(typeSpec, preservedConstants, emittedNames);
        ClassName self = constantSpec.outputClass();
        ParameterizedTypeName translatorType = ParameterizedTypeName.get(
                ClassName.get("dev.wyck.wrapper", "RegisteredConstantTranslator"),
                self
        );
        typeSpec.addField(FieldSpec.builder(translatorType, "TRANSLATOR", javax.lang.model.element.Modifier.PUBLIC, javax.lang.model.element.Modifier.STATIC, javax.lang.model.element.Modifier.FINAL)
                .initializer("$T.of($T.%s, $T::resourceKey, $T.values())"
                                .formatted(constantSpec.registryId()),
                        ClassName.get("dev.wyck.wrapper", "RegisteredConstantTranslator"),
                        ClassName.get("dev.wyck.registry.internal", "RegistryId"),
                        self,
                        self
                ).build()
        );
        typeSpec.addMethod(MethodSpec.methodBuilder("translator")
                .addAnnotation(
                        AnnotationSpec.builder(ClassName.get("dev.wyck.annotations", "AsOf"))
                                .addMember("value", "$S", memberVersion(existingVersions, "translator", constantSpec))
                                .build()
                ).addAnnotation(Override.class)
                .returns(translatorType)
                .addModifiers(javax.lang.model.element.Modifier.PUBLIC)
                .addCode("return TRANSLATOR;")
                .build()
        );
        appendEnumConstructor(typeSpec, constantSpec, existingVersions);
        typeSpec.addMethod(MethodSpec.methodBuilder("key")
                .addAnnotation(
                        AnnotationSpec.builder(ClassName.get("dev.wyck.annotations", "AsOf"))
                                .addMember("value", "$S", memberVersion(existingVersions, "key", constantSpec))
                                .build()
                )
                .returns(String.class)
                .addCode("return this.key;")
                .addModifiers(javax.lang.model.element.Modifier.PUBLIC)
                .addJavadoc("""
                        The vanilla registry path for this activity.
                        @return the registry path for this activity
                        @since $L
                        """, memberVersion(existingVersions, "key", constantSpec))
                .build()
        );
        typeSpec.addMethod(MethodSpec.methodBuilder("resourceKey")
                .addAnnotation(
                        AnnotationSpec.builder(ClassName.get("dev.wyck.annotations", "AsOf"))
                                .addMember("value", "$S", memberVersion(existingVersions, "resourceKey", constantSpec))
                                .build()
                )
                .returns(ClassName.get("dev.wyck.keys", "ResourceKey"))
                .addModifiers(javax.lang.model.element.Modifier.PUBLIC)
                .addCode("return $T.minecraft(this.key);", ClassName.get("dev.wyck.keys", "ResourceKey"))
                .build()
        );
    }

    private static void appendEnumFields(EnumSpec enumSpec, TypeSpec.Builder typeSpec, List<String> preservedConstants, Map<String, String> existingVersions) {
        Enum<?>[] constants = enumSpec.sourceEnum().getEnumConstants();
        Set<String> emittedNames = new HashSet<>();
        for (Enum<?> constant : constants) {
            String key = enumSpec.keyExtractor().apply(constant);
            typeSpec.addEnumConstant(constant.name(), TypeSpec.anonymousClassBuilder("$S", key)
                    .build()
            );
            emittedNames.add(constant.name());
        }
        appendPreservedEnumConstants(typeSpec, preservedConstants, emittedNames);
        ClassName self = enumSpec.outputClass();
        ParameterizedTypeName translatorType = ParameterizedTypeName.get(
                ClassName.get("dev.wyck.wrapper", "KeyedEnumTranslator"),
                self
        );
        typeSpec.addField(FieldSpec.builder(translatorType, "TRANSLATOR", javax.lang.model.element.Modifier.PUBLIC, javax.lang.model.element.Modifier.STATIC, javax.lang.model.element.Modifier.FINAL)
                .initializer("$T.byKey($T::getKey, $T.values())",
                        ClassName.get("dev.wyck.wrapper", "KeyedEnumTranslator"),
                        self,
                        self
                ).build()
        );
        typeSpec.addMethod(MethodSpec.methodBuilder("translator")
                .addAnnotation(
                        AnnotationSpec.builder(ClassName.get("dev.wyck.annotations", "AsOf"))
                                .addMember("value", "$S", memberVersion(existingVersions, "translator", enumSpec))
                                .build()
                ).addAnnotation(Override.class)
                .returns(translatorType)
                .addCode("return TRANSLATOR;")
                .addModifiers(javax.lang.model.element.Modifier.PUBLIC)
                .build()
        );
        typeSpec.addMethod(MethodSpec.methodBuilder("getKey")
                .addAnnotation(
                        AnnotationSpec.builder(ClassName.get("dev.wyck.annotations", "AsOf"))
                                .addMember("value", "$S", memberVersion(existingVersions, "key", enumSpec))
                                .build()
                )
                .returns(String.class)
                .addCode("return this.key;")
                .addModifiers(javax.lang.model.element.Modifier.PUBLIC)
                .addJavadoc("""
                                 The vanilla name for this $L
                                 @return the vanilla key for this enum value
                                 @since $L
                                """,
                        enumSpec.sourceEnum().getSimpleName(),
                        memberVersion(existingVersions, "key", enumSpec)
                )
                .build()
        );
        appendEnumConstructor(typeSpec, enumSpec, existingVersions);
    }

    private static void appendReferenceFields(ReferenceSpec referenceSpec, TypeSpec.Builder typeSpec, Map<String, String> existingVersions) throws IllegalAccessException {
        // guards against duplicate field names across source classes
        Set<String> emittedNames = new HashSet<>();
        for (Class<?> sourceClass : referenceSpec.sourceClasses()) {
            CodeBlock firstComment = CodeBlock.of("From: $L", sourceClass.getSimpleName());
            for (Field field : sourceClass.getDeclaredFields()) {
                // only static fields
                if (!Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                if (referenceSpec.fieldFilter() != null) {
                    if (!referenceSpec.fieldFilter().test(field)) {
                        continue;
                    }
                } else if (!referenceSpec.registryType().isAssignableFrom(field.getType())) {
                    continue;
                }

                field.setAccessible(true);
                Object entry = field.get(null);

                Identifier key = referenceSpec.registryLookup().apply(entry);
                if (key == null) {
                    System.err.println("warning: no registry key for " + field.getName() + ", skipping");
                    continue;
                }

                if (!emittedNames.add(field.getName())) {
                    System.err.println("warning: duplicate field name " + field.getName() + " in " + sourceClass.getSimpleName() + ", skipping");
                    continue;
                }

                // existing fields keep their original @AsOf; new fields get the current version
                String fieldVersion = existingVersions.getOrDefault(field.getName(), referenceSpec.since());
                FieldSpec.Builder fieldSpec = FieldSpec.builder(referenceSpec.typeClass(), field.getName())
                        .addAnnotation(AnnotationSpec.builder(ClassName.get("dev.wyck.annotations", "AsOf"))
                                .addMember("value", "$S", fieldVersion)
                                .build()
                        )
                        .initializer("reference($S)", key.getPath())
                        .addModifiers(javax.lang.model.element.Modifier.PUBLIC, javax.lang.model.element.Modifier.STATIC, javax.lang.model.element.Modifier.FINAL);
                if (firstComment != null) {
                    fieldSpec.addJavadoc(firstComment);
                    firstComment = null;
                }
                typeSpec.addField(fieldSpec.build());
            }
        }
        CodeBlock referenceCode;
        ClassName typeName = referenceSpec.typeClass();
        ClassName resourceKey = ClassName.get("dev.wyck.keys", "ResourceKey");
        if (referenceSpec.typeClass().equals(resourceKey)) {
            referenceCode = CodeBlock.of("return $T.minecraft(path);", resourceKey);
        } else if (referenceSpec.keyChain() == null) {
            referenceCode = CodeBlock.of("return $T.reference($T.minecraft(path));",
                    typeName,
                    resourceKey
            );
        } else {
            referenceCode = CodeBlock.builder().addStatement("$T keyed = $T.reference($T.minecraft(path))",
                            typeName,
                            typeName,
                            resourceKey
                    ).addStatement("$T.$L.append(keyed)",
                            ClassName.get("dev.wyck.keys", "KeyChains"),
                            referenceSpec.keyChain()
                    ).addStatement("return keyed")
                    .build();
        }
        typeSpec.addMethod(MethodSpec.methodBuilder("reference")
                .addModifiers(javax.lang.model.element.Modifier.PRIVATE, javax.lang.model.element.Modifier.STATIC)
                .addParameter(ParameterSpec.builder(String.class, "path").build())
                .returns(typeName)
                .addCode(referenceCode)
                .build()
        );
    }

    // existing members keep their original @AsOf; new members get the spec's since version
    private static String memberVersion(Map<String, String> existingVersions, String memberName, GeneratorSpec spec) {
        return existingVersions.getOrDefault(memberName, spec.since());
    }
}