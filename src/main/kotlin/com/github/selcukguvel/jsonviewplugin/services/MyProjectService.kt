package com.github.selcukguvel.jsonviewplugin.services

import com.github.selcukguvel.jsonviewplugin.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
