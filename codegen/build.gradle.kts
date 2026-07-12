plugins {
    alias(libs.plugins.paperweight.userdev)
}

group = "dev.wyck.codegen"

dependencies {
    paperweight.paperDevBundle(libs.versions.minecraft.v26.m2)
}

// may differ across paperweight-userdev versions
val mojangMappedServerConfig = listOf("mojangMappedServer", "mojangMappedServerRuntime",).firstNotNullOfOrNull { name ->
    configurations.findByName(name)
} ?: error(
    "could not find the Mojang-mapped server configuration. Run './gradlew :${project.name}:dependencies' and use the correct configuration name"
)

val generatedCatalogFiles = listOf(
    "dev/wyck/wrapper/worldgen/feature/ConfiguredFeatures.java",
    "dev/wyck/wrapper/worldgen/placement/PlacedFeatures.java",
    "dev/wyck/wrapper/worldgen/function/DensityFunctions.java",
    "dev/wyck/wrapper/worldgen/synth/Noises.java",
    "dev/wyck/wrapper/environment/sounds/SoundEvents.java"
).map { rootProject.file("api/src/main/java/$it") }

tasks.register<JavaExec>("generateSources") {
    group = "codegen"
    description = "Generates source files from vanilla registries"

    classpath = sourceSets.main.get().output + mojangMappedServerConfig

    mainClass.set("dev.wyck.codegen.ReferenceGenerator")
    args(
        rootProject.file("api/src/main/java").absolutePath,
        rootProject.version.toString()
    )

    inputs.files(sourceSets.main.get().allSource)
    outputs.files(generatedCatalogFiles)

    dependsOn(tasks.compileJava)
}