plugins {
    id("java")
}

group = "com.github.tozymc"
version = "0.0.1-SNAPSHOT"


subprojects {
    apply(plugin = "java-libary")

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
}
