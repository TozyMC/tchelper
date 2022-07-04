plugins {
    id("java-library")
}

group = "com.github.tozymc"
version = "0.0.1-SNAPSHOT"


subprojects {
    apply(plugin = "java-library")

    dependencies {
        compileOnlyApi(libs.jetbrains.annotations)

        api(libs.guava)
        api(libs.slf4j)
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
