val bundledSourceProjects = listOf(":api")
val minecraft = ":minecraft"
val minecraftProjects = project(minecraft)
    .subprojects
    .map { it.name }

dependencies {
    val libs = rootProject.libs
    api(project(":api"))
    api(project(":commons"))

    // NMS Implementations
    for (project in minecraftProjects) {
        api(project(path = "${minecraft}:${project}"))
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

tasks.named<Jar>("jar") {
    enabled = false
}


configurations {
    apiElements { outgoing.artifacts.clear(); outgoing.artifact(tasks.shadowJar) }
    runtimeElements { outgoing.artifacts.clear(); outgoing.artifact(tasks.shadowJar) }
}

tasks.shadowJar {
    relocate("org.objectweb.asm", "me.outspending.biomesapi.asm")
    exclude("com/google/**")
    minimize {
        exclude(project(":api"))
        exclude(project(":commons"))
        for (project in minecraftProjects) {
            exclude(project("${minecraft}:${project}"))
        }
        exclude("META-INF/**")
    }
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
        if (repo == null || user == null || pass == null) return@publications

        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "BiomesAPI"
            version = project.version.toString()

            artifact(tasks.shadowJar.get().archiveFile) {
                builtBy(tasks.shadowJar)
            }

            artifact(tasks.named("sourcesJar").get())
        }
    }
}