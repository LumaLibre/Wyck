plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "BiomesAPI"

include("main")
include("test-plugin")
include("minecraft:Wrapper")
include("minecraft:1.21_R7")