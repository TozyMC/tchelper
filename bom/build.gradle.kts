plugins {
    `java-platform`
    id("tchelper.publishing-conventions")
}

dependencies {
    constraints {
        rootProject.subprojects.forEach {
            if (it == project) {
                return@forEach
            }

            api(project(it.path))
        }
    }
}
