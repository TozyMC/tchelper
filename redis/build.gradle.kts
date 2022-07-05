plugins {
    id("tchelper.common-conventions")
}

dependencies {
    api(projects.tchelperPath)
    api(libs.redisson)

    runtimeOnly(libs.kryo5)
}