// TODO: remove legacy versions & cleanup
plugins {
    java
    `maven-publish`
    id("com.gradleup.shadow") version "9.0.0-beta9"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19"
    id("me.champeau.jmh") version "0.7.2"
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "com.gradleup.shadow")

    group = "me.outspending.biomesapi"
    version = "0.0.6"

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    // The file for publication.
    val reobfBuildFile = project.layout.buildDirectory.file("libs/reobf/${project.name}-${project.version}.jar")

    // Check if a project has the reobfJar task.
    fun Project.usesReobfuscatedJar() : Boolean {
        return false
//        return try {
//            tasks.named("reobfJar")
//            true
//        } catch (ignored: UnknownTaskException) {
//            false
//        }
    }

    // needs to be in after evaluate, otherwise the project's reobfJar task (if it exists)
    // won't be there.
    afterEvaluate {

        // publish NMS apis, and the main project API
        // this is required because consumers require getting the POM
        // files from this project's dependencies, even if they"re completely
        // shaded and included in this file.
        //
        // not optimal because this leads to more network calls when downloading
        // the api for the first time. ideally we only publish the main api.
//        publishing {
//            publications {
//                create<MavenPublication>("maven") {
//                    groupId = "me.outspending.biomesapi"
//                    artifactId = project.name
//
//                    if (usesReobfuscatedJar()) artifact(reobfBuildFile)
//                    from(components["java"])
//                }
//            }
//        }

        // Throws an error if we don"t explicitly say we're using the output
        // of reobfJar in the final build.
        tasks.named("publishMavenPublicationToMavenLocal") {
            if (usesReobfuscatedJar()) {
                dependsOn(tasks.reobfJar)
            }
        }

        // Don't use the task if it doesn't exist on this project
        if (usesReobfuscatedJar()) {
            tasks.reobfJar {
                outputJar.set(reobfBuildFile)
            }
        }

        // Run reobfJar on applicable projects
        tasks.assemble {
            if (usesReobfuscatedJar()) dependsOn(tasks.reobfJar)
        }
    }

    repositories {
        mavenCentral()
        maven {
            name = "papermc-repo"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
        maven {
            name = "sonatype"
            url = uri("https://oss.sonatype.org/content/groups/public/")
        }
        maven {
            name = "codemc-releases"
            url = uri("https://repo.codemc.io/repository/maven-releases/")
        }
    }

}

val nmsVersions = listOf("1.19_R2", "1.19_R3", "1.20_R1", "1.20_R2", "1.20_R3", "1.21_R3", "1.21_R9")
dependencies {
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
    implementation("com.google.guava:guava:11.0.2")


    // NMS Implementations
    implementation(project(":NMS:Wrapper"))
    for (version in nmsVersions) {
        implementation(project(path = ":NMS:${version}"))
    }

    // JMH Implementations
    jmh("org.openjdk.jmh:jmh-core:0.9")
    jmh("org.openjdk.jmh:jmh-generator-annprocess:0.9")

}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

publishing {
    val repo: String? = System.getenv("REPO_URL")
    val user: String? = System.getenv("REPO_USERNAME")
    val pass: String? = System.getenv("REPO_PASSWORD")


    repositories {
        if (repo == null || user == null || pass == null) {
            println("$repo, $user, $pass")
            println("Skipping publication configuration, REPO_URL, REPO_USERNAME or REPO_PASSWORD is not set.")
            return@repositories
        }
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
        if (repo == null || user == null || pass == null) {
            return@publications
        }
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            artifact(tasks.shadowJar.get().archiveFile) {
                builtBy(tasks.shadowJar)
            }
        }
    }
}