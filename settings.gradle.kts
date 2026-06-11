plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "BiomesAPI"

include(":api")
include(":commons")
include(":minecraft:1_21_11")
include(":minecraft:26_1")
include(":test-plugin")
include("paper")
include("codegen")
include("minecraft:26_2")