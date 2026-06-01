plugins {
    alias(libs.plugins.paperweight.userdev)
}

val minecraft = ":minecraft"
val minecraftProjects = project(minecraft)
    .subprojects
    .map { it.name }

dependencies {
    val libs = rootProject.libs
    compileOnly(libs.protocollib)
    compileOnly(libs.packetevents)
    api(project(":api"))

    // NMS Implementations
    for (project in minecraftProjects) {
        implementation(project(path = "${minecraft}:${project}"))
    }
    paperweight.paperDevBundle(libs.versions.minecraft.v26.m1.r2)
}

java {
    withSourcesJar()
}

val bundledSourceProjects = listOf(":api")

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

tasks.shadowJar {
    relocate("dev.faststats", "me.outspending.biomesapi.metrics")
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