package com.ldw.theme.api

import android.content.Context
import android.util.AttributeSet
import android.view.View

fun interface ICreateView {
    fun realCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View?
}