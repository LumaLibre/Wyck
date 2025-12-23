plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19"
    id("me.champeau.jmh") version "0.7.2"
}


val nmsVersions = listOf("1.19_R2", "1.19_R3", "1.20_R1", "1.20_R2", "1.20_R3", "1.21_R3", "1.21_R9")
dependencies {
    paperweight.paperDevBundle("1.21.11-R0.1-SNAPSHOT")
    implementation("com.google.guava:guava:11.0.2")
    compileOnly("net.dmulloy2:ProtocolLib:5.4.0")
    compileOnly("com.github.retrooper:packetevents-spigot:2.11.1")

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
            artifactId = "BiomesAPI"
            version = project.version.toString()
            artifact(tasks.shadowJar.get().archiveFile) {
                builtBy(tasks.shadowJar)
            }
        }
    }
}