enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    includeBuild("build-logic")
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
}

rootProject.name = "tchelper-parent"

sequenceOf("bom", "path", "config", "redis", "sql").forEach {
    val subProjectName = "tchelper-$it"
    include(subProjectName)
    project(":$subProjectName").projectDir = file(it)
}
