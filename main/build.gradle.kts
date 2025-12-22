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
    compileOnly("com.github.retrooper:packetevents-spigot:2.7.0")
    compileOnly(files("local/RealisticSeasons.jar")) // decompilation purposes only

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