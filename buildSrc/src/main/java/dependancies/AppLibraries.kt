package dependancies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.libraries() {

    androidX()
    daggerHilt()
    glide()
    gson()
    materialDesign()
    NavGraph()
    okHttp()
    retrofit()
    testUnit()
    paging()
    room()
}