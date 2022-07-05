plugins {
    id("java-library")
    id("org.javamodularity.moduleplugin") version "1.8.11" apply false
}

group = "com.github.tozymc"
version = "0.0.1-SNAPSHOT"

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "org.javamodularity.moduleplugin")

    dependencies {
        compileOnlyApi(rootProject.libs.jetbrains.annotations)

        api(rootProject.libs.guava)
        api(rootProject.libs.slf4j)
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

    configure<org.javamodularity.moduleplugin.extensions.ModularityExtension> {
        moduleVersion(rootProject.version as String)
    }
}
