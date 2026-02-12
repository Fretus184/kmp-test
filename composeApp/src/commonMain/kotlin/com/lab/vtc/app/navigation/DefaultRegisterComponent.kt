package com.lab.vtc.app.navigation

import com.arkivanov.decompose.ComponentContext

interface RegisterComponent {
    fun onRegisterSuccess()
}

class DefaultRegisterComponent(
    context: ComponentContext,
    private val onFinished: () -> Unit
) : RegisterComponent, ComponentContext by context {
    override fun onRegisterSuccess() {
        onFinished()
    }
}