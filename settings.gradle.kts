plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "Wyck"

include(":api")
include(":runtime")
include(":decoders")
include(":bundle")
include(":codegen")
include(":paper")
include(":tests")
include(":test-plugin")
