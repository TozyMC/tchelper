plugins {
    signing
    `maven-publish`
}

signing {
    sign(publishing.publications)
}

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
        register<MavenPublication>("gpr") {
            from(components["java"])
            pom {
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
