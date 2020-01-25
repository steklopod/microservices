rootProject.name = "microservices"

include(
    "config-server",
    "ui-gateway",
    "eureka-server",
    "items-ui",
    "items-service"
)
plugins {
    id("com.gradle.enterprise").version("3.1.1")
}
gradleEnterprise {
    buildScan {
        link("VCS", "https://github.com/steklopod/microservices.git")
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        allowUntrustedServer = true
        termsOfServiceAgree = "yes"
        publishAlways()
    }
}
