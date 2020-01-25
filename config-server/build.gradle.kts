description = "config-server"

plugins {
    jacoco
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.springframework.boot")
}

dependencies {
    val springCloud: String by project
    val springBoot: String by project
    implementation(kotlin("stdlib"))
    implementation(enforcedPlatform("org.springframework.boot:spring-boot-dependencies:$springBoot"))
    implementation(enforcedPlatform("org.springframework.cloud:spring-cloud-dependencies:$springCloud"))

    implementation("org.springframework.cloud:spring-cloud-config-server")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    val java: String by project
    val barCoverage: String by project
    test { useJUnitPlatform(); finalizedBy(jacocoTestReport) }
    compileKotlin { kotlinOptions { jvmTarget = java }; sourceCompatibility = java; targetCompatibility = java }
    compileTestKotlin { kotlinOptions { jvmTarget = java }; sourceCompatibility = java; targetCompatibility = java }
    withType<JacocoCoverageVerification> { violationRules { rule { limit { minimum = barCoverage.toBigDecimal() } } } }
}
