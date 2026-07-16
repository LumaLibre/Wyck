plugins {
    alias(libs.plugins.paperweight.userdev)
}

dependencies {
    val libs = rootProject.libs
    api(project(":api"))
    compileOnly(libs.protocollib)
    compileOnly(libs.packetevents)

    paperweight.paperDevBundle(libs.versions.minecraft.v26.m2)
}