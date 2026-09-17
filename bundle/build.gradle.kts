import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val bundledSourceProjects = listOf(":api")

data class ModulePublication(
    val name: String,
    val artifactId: String,
    val projectPath: String,
    val dependencies: List<String> = emptyList(),
)

val modulePublications = listOf(
    ModulePublication("wyckApi", "wyck-api", ":api"),
    ModulePublication("wyckRuntime", "wyck-runtime", ":runtime", listOf("wyck-api")),
    ModulePublication(
        "wyckDecoders",
        "wyck-decoders",
        ":decoders",
        listOf("wyck-api", "wyck-runtime"),
    ),
)
val wyckVanillaProjects = listOf(":api", ":runtime")

val wyckVanilla by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

dependencies {
    val libs = rootProject.libs
    api(project(":api"))
    api(project(":runtime"))
    api(project(":decoders"))

    wyckVanillaProjects.forEach { path ->
        add(wyckVanilla.name, project(path))
    }
}

java {
    withSourcesJar()
}

tasks.named<Jar>("sourcesJar") {
    bundledSourceProjects.forEach { path ->
        val proj = project(path)
        val main = proj.extensions
            .getByType<SourceSetContainer>()
            .getByName("main")
        from(main.allSource)
    }
    dependsOn(bundledSourceProjects.map { project(it).tasks.named("classes") })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

val moduleSourcesJars = modulePublications.associate { publication ->
    publication.name to tasks.register<Jar>("${publication.name}SourcesJar") {
        archiveBaseName.set(publication.artifactId)
        archiveClassifier.set("sources")
        val sourceProject = project(publication.projectPath)
        val main = sourceProject.extensions
            .getByType<SourceSetContainer>()
            .getByName("main")
        from(main.allSource)
        dependsOn(sourceProject.tasks.named("classes"))
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

val wyckVanillaSourcesJar by tasks.registering(Jar::class) {
    archiveBaseName.set("wyck-basic")
    archiveClassifier.set("sources")
    wyckVanillaProjects.forEach { path ->
        val sourceProject = project(path)
        val main = sourceProject.extensions
            .getByType<SourceSetContainer>()
            .getByName("main")
        from(main.allSource)
    }
    dependsOn(wyckVanillaProjects.map { project(it).tasks.named("classes") })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.named<Jar>("jar") {
    enabled = false
}

tasks.withType<PublishToMavenRepository>().configureEach {
    dependsOn(":tests:wireProviderTest")
}

configurations {
    apiElements { outgoing.artifacts.clear(); outgoing.artifact(tasks.shadowJar) }
    runtimeElements { outgoing.artifacts.clear(); outgoing.artifact(tasks.shadowJar) }
}

tasks.shadowJar {
    exclude("com/google/**")
    minimize {
        exclude(project(":api"))
        exclude(project(":runtime"))
        exclude(project(":decoders"))
        exclude("META-INF/**")
    }
}

val wyckVanillaJar by tasks.registering(ShadowJar::class) {
    archiveBaseName.set("wyck-vanilla")
    archiveClassifier.set("")
    configurations = listOf(wyckVanilla)
    exclude("com/google/**")
    minimize {
        wyckVanillaProjects.forEach { path ->
            exclude(project(path))
        }
        exclude("META-INF/**")
    }
}

tasks.build {
    dependsOn(wyckVanillaJar, wyckVanillaSourcesJar, moduleSourcesJars.values)
}

publishing {
    val repo: String? = System.getenv("REPO_URL")
    val user: String? = System.getenv("REPO_USERNAME")
    val pass: String? = System.getenv("REPO_PASSWORD")


    repositories {
        if (repo == null || user == null || pass == null) return@repositories

        maven {
            url = uri(repo)
            credentials(PasswordCredentials::class) {
                username = user
                password = pass
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "wyck"
            version = project.version.toString()

            artifact(tasks.shadowJar.get().archiveFile) {
                builtBy(tasks.shadowJar)
            }

            artifact(tasks.named("sourcesJar").get())
        }

        // TODO: Remove before 3.3.0
        create<MavenPublication>("wyckUppercase") {
            groupId = project.group.toString()
            artifactId = "Wyck"
            version = project.version.toString()

            artifact(tasks.shadowJar.get().archiveFile) {
                builtBy(tasks.shadowJar)
            }

            artifact(tasks.named("sourcesJar").get())
        }

        modulePublications.forEach { module ->
            create<MavenPublication>(module.name) {
                groupId = project.group.toString()
                artifactId = module.artifactId
                version = project.version.toString()

                artifact(project(module.projectPath).tasks.named<Jar>("jar"))
                artifact(moduleSourcesJars.getValue(module.name))

                if (module.dependencies.isNotEmpty()) {
                    pom.withXml {
                        val dependencies = asNode().appendNode("dependencies")
                        module.dependencies.forEach { dependencyId ->
                            val dependency = dependencies.appendNode("dependency")
                            dependency.appendNode("groupId", project.group.toString())
                            dependency.appendNode("artifactId", dependencyId)
                            dependency.appendNode("version", project.version.toString())
                            dependency.appendNode("scope", "compile")
                        }
                    }
                }
            }
        }

        create<MavenPublication>("wyckVanilla") {
            groupId = project.group.toString()
            artifactId = "wyck-vanilla"
            version = project.version.toString()

            artifact(wyckVanillaJar.get().archiveFile) {
                builtBy(wyckVanillaJar)
            }

            artifact(wyckVanillaSourcesJar.get())
        }
    }
}
