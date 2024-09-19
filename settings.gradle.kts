plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "lms"

include("common")
include("core:api")
include("core:domain")
include("core:persistence")
include("infrastructure")
include("core-store")
include("core-store:domain")
include("core-store:api")
