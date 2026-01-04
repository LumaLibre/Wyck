val minecraft = ":minecraft"
val minecraftProjects = project(minecraft)
    .subprojects
    .map { it.name }

dependencies {
    val libs = rootProject.libs
    compileOnly(libs.protocollib)
    compileOnly(libs.packetevents)

    // NMS Implementations
    for (project in minecraftProjects) {
        implementation(project(path = "${minecraft}:${project}"))
    }
}

java {
    withSourcesJar()
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