dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        gradlePluginPortal()
        maven(url = uri("https://packages.jetbrains.team/maven/p/jcs/maven"))
    }

    versionCatalogs {
        register("libs") {
            from(files("../gradle/libs.versions.toml")) // include from parent project
        }
    }
}

rootProject.name = "tchelper-build-logic"
