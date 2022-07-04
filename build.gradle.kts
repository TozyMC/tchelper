plugins {
    id("java-library")
}

group = "com.github.tozymc"
version = "1.0-SNAPSHOT"

dependencies {
}

val targetJavaVersion = 17
java {
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
