plugins {
    alias(libs.plugins.paperweight.userdev)
}

dependencies {
    val libs = rootProject.libs
    api(project(":api"))
    implementation(libs.org.ow2.asm)
    compileOnly(libs.protocollib)
    compileOnly(libs.packetevents)

    paperweight.paperDevBundle(libs.versions.minecraft.v26.m2)
}