plugins {
    alias(libs.plugins.paperweight.userdev)
}

dependencies {
    paperweight.paperDevBundle(libs.versions.minecraft.v26.m1.r2)
}

// may differ across paperweight-userdev versions
val mojangMappedServerConfig = listOf("mojangMappedServer", "mojangMappedServerRuntime",).firstNotNullOfOrNull { name ->
    configurations.findByName(name)
} ?: error(
    "could not find the Mojang-mapped server configuration. Run './gradlew :${project.name}:dependencies' and use the correct configuration name"
)

val generatedCatalogFiles = listOf(
    "me/outspending/biomesapi/wrapper/worldgen/feature/ConfiguredFeatures.java",
    "me/outspending/biomesapi/wrapper/worldgen/placement/PlacedFeatures.java",
    "me/outspending/biomesapi/wrapper/level/noise/function/DensityFunctions.java",
    "me/outspending/biomesapi/wrapper/level/noise/Noises.java",
).map { rootProject.file("api/src/main/java/$it") }

tasks.register<JavaExec>("generateSources") {
    group = "codegen"
    description = "Generates source files from vanilla registries"

    classpath = sourceSets.main.get().output + mojangMappedServerConfig

    mainClass.set("me.outspending.biomesapi.codegen.ReferenceGenerator")
    args(
        rootProject.file("api/src/main/java").absolutePath,
        rootProject.version.toString()
    )

    inputs.files(sourceSets.main.get().allSource)
    outputs.files(generatedCatalogFiles)

    dependsOn(tasks.compileJava)
}