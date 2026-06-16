plugins {
    alias(libs.plugins.paperweight.userdev)
}

group = "me.outspending.biomesapi.v1_21_11"

dependencies {
    compileOnly(project(":api"))
    paperweight.paperDevBundle(libs.versions.minecraft.v1.m21.r11)
}

//java {
//    toolchain.languageVersion = JavaLanguageVersion.of(21)
//}