package com.example.lib

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyClass : Plugin<Project> {
    override fun apply(target: Project) {
        println("DDDDDD")
    }

}