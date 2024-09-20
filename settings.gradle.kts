plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "lms"

include("common")
include("infrastructure")
include("gateway")
include("eureka-server")

include("core:api")
include("core:domain")
include("core:persistence")
include("core-store:domain")
include("core-store:api")
include("client")
