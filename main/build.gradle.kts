plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19"
    id("me.champeau.jmh") version "0.7.2"
}


val nmsVersions = listOf("1.19_R2", "1.19_R3", "1.20_R1", "1.20_R2", "1.20_R3", "1.21_R3")
dependencies {
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
    implementation("com.google.guava:guava:11.0.2")
    compileOnly("com.github.retrooper:packetevents-spigot:2.7.0")
    compileOnly(files("local/RealisticSeasons.jar"))

    // NMS Implementations
    implementation(project(":NMS:Wrapper"))
    for (version in nmsVersions) {
        implementation(project(path = ":NMS:${version}", configuration = "reobf"))
    }

    // JMH Implementations
    jmh("org.openjdk.jmh:jmh-core:0.9")
    jmh("org.openjdk.jmh:jmh-generator-annprocess:0.9")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}