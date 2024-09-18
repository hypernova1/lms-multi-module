plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "lms"

include("common")
include("persistence")
include("core:api")
include("core:domain")
include("infrastructure")
