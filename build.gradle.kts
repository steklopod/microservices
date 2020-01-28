description = "ROOT"

plugins {
    val kotlin = "1.3.61"
    val (springBoot, management) = "2.2.4.RELEASE" to "1.0.9.RELEASE"
    kotlin("jvm") version kotlin apply false
    kotlin("plugin.spring") version kotlin apply false
    id("org.springframework.boot") version springBoot apply false
    id("io.spring.dependency-management") version management apply false
}

subprojects {
    repositories { mavenCentral(); mavenLocal(); jcenter() }
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")
}
