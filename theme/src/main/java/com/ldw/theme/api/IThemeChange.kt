package com.ldw.theme.api

interface IThemeChange {
    companion object {
        const val ID = 0
    }
    fun onThemeChange()
}
