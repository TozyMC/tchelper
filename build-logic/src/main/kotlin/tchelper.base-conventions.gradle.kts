plugins {
    `java-library`
    id("org.javamodularity.moduleplugin")
}

val projectVersion: String by rootProject
val projectDescription: String by rootProject

group = "com.github.tozymc"
version = projectVersion
description = projectDescription

// expose version catalog
val libs = extensions.getByType(org.gradle.accessors.dm.LibrariesForLibs::class)

dependencies {
    compileOnlyApi(libs.jetbrains.annotations)

    api(libs.guava)
    api(libs.slf4j)
}

val targetJavaVersion = 17
java {
    withSourcesJar()

    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
        vendor.set(JvmVendorSpec.AZUL)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(targetJavaVersion)
}

configure<org.javamodularity.moduleplugin.extensions.ModularityExtension> {
    moduleVersion(projectVersion)
}
