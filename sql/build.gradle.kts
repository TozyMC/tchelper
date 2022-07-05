plugins {
    id("tchelper.common-conventions")
}

dependencies {
    compileOnlyApi(libs.bundles.adventure)

    api(projects.tchelperPath)
    api(libs.bundles.hibernate)
}