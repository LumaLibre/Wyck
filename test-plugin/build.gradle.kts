plugins {
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19"
}

dependencies {
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
    implementation(project(":main"))
}

tasks.shadowJar {
    dependencies {

    }
    archiveClassifier.set("")
}

tasks.jar {
    enabled = false
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.reobfJar {

}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}