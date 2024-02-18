package com.example.lib

import org.gradle.api.Plugin
import org.gradle.api.Project

class ReplaceSuperClassPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("Hello0 from the ReplaceSuperClassPlugin")
        target.task("hello") {
            it.doLast {
                println("Hello1 from the ReplaceSuperClassPlugin")
            }
        }
    }

}