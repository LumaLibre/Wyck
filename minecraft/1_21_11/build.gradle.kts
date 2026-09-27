plugins {
    alias(libs.plugins.paperweight.userdev)
}

group = "dev.wyck.v1_21_11"

dependencies {
    compileOnly(project(":api"))
    compileOnly(project(":commons"))
    compileOnly(project(":decoders"))
    paperweight.paperDevBundle(libs.versions.minecraft.v1.m21.r11)
}
