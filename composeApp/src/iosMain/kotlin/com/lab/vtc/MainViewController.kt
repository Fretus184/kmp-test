package com.lab.vtc

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.lab.vtc.app.navigation.DefaultRootComponent

fun MainViewController() = ComposeUIViewController {
    val lifeccycle = LifecycleRegistry()
    val root = DefaultRootComponent(DefaultComponentContext(lifeccycle))
    App(root)
}