plugins {
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19"
}

dependencies {
    paperweight.paperDevBundle("1.20.3-R0.1-SNAPSHOT")

    compileOnly(project(":NMS:Wrapper"))
}
tasks.reobfJar {}