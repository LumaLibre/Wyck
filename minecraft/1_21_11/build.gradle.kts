plugins {
    alias(libs.plugins.paperweight.userdev)
}

dependencies {
    compileOnly(project(":api"))
    paperweight.paperDevBundle(libs.versions.minecraft.v1.m21.r11)
}

//java {
//    toolchain.languageVersion = JavaLanguageVersion.of(21)
//}