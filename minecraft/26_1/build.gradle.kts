plugins {
    alias(libs.plugins.paperweight.userdev)
}

group = "me.outspending.biomesapi.v26_1"

dependencies {
    compileOnly(project(":api"))
    paperweight.paperDevBundle(libs.versions.minecraft.v26.m1.r2)
}
