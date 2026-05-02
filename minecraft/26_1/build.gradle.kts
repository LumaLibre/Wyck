plugins {
    alias(libs.plugins.paperweight.userdev)
}

dependencies {
    compileOnly(project(":api"))
    paperweight.paperDevBundle(libs.versions.minecraft.v26.m1.r2)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}