plugins {
    alias(libs.plugins.paperweight.userdev)
}

group = "me.outspending.biomesapi.v26_2"

dependencies {
    compileOnly(project(":api"))
    compileOnly(project(":commons"))
    paperweight.paperDevBundle(libs.versions.minecraft.v26.m2)
}
