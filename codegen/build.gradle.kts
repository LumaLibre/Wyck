plugins {
    application

    alias(libs.plugins.paperweight.userdev)
}

group = "dev.wyck.codegen"

dependencies {
    paperweight.paperDevBundle(libs.versions.minecraft.v26.m2)
    implementation("com.palantir.javapoet:javapoet:0.19.0")
    implementation("org.jetbrains:annotations:26.1.0")
}

// may differ across paperweight-userdev versions
val mojangMappedServerConfig = listOf("mojangMappedServer", "mojangMappedServerRuntime").firstNotNullOfOrNull { name ->
    configurations.findByName(name)
} ?: error(
    "could not find the Mojang-mapped server configuration. Run './gradlew :${project.name}:dependencies' and use the correct configuration name"
)

tasks {
    application {
        mainClass = "dev.wyck.codegen.ReferenceGenerator"
    }

    getByName<JavaExec>("run") {
        args = listOf(
            rootProject.file("api/src/main/java").absolutePath,
            rootProject.version.toString()
        )
        dependsOn("dependencies")
        classpath = mojangMappedServerConfig + classpath
    }

    register<Task>("generateSources") {
        dependsOn("run")
    }
}