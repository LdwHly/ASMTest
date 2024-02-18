package com.example.lib

import org.gradle.api.Plugin
import org.gradle.api.Project

class ReplaceSuperClassPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.task("hello") {
            it.doLast {
                println("Hello from the GreetingPlugin")
            }
        }
    }

}