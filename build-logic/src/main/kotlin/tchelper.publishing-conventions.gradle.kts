plugins {
    signing
    `maven-publish`
}

signing {
    sign(publishing.publications)
}

fun String.capitalizeWords(): String = split("-").joinToString(" ") { it.capitalize() }

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/TozyMC/tchelper")
            credentials {
                username =
                    project.findProperty("gh.user") as String? ?: System.getenv("GH_USERNAME")
                password = project.findProperty("gh.token") as String? ?: System.getenv("GH_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("gpr") {
            // configure for libraries
            pluginManager.withPlugin("java") {
                from(components["java"])
            }
            // configure for BOM
            pluginManager.withPlugin("java-platform") {
                from(components["javaPlatform"])
            }

            pom {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()

                name.set(project.name.capitalizeWords())
                description.set(project.description)
                url.set("https://github.com/TozyMC/tchelper/")

                licenses {
                    license {
                        name.set("MIT license")
                        url.set("https://github.com/TozyMC/tchelper/blob/main/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("TozyMC")
                        name.set("Nguyễn Thanh Tân")
                        email.set("tozymc@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/TozyMC/tchelper.git")
                    developerConnection.set("scm:git:ssh://github.com/TozyMC/tchelper.git")
                    url.set("https://github.com/TozyMC/tchelper/")
                }
            }
        }
    }
}
