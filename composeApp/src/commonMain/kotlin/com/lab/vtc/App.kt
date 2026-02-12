package com.lab.vtc

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.lab.vtc.app.navigation.RootComponent
import com.lab.vtc.ui.main.MainView
import com.lab.vtc.ui.register.RegisterView

@Composable
@Preview
fun App(rootComponent: RootComponent) {

    MaterialTheme {
        Children(
            stack = rootComponent.stack,
            animation = stackAnimation(fade() + slide())
        ) { child ->
            when (val instance = child.instance) {
                is RootComponent.Child.VTCMain -> MainView(instance.component)
                is RootComponent.Child.VTCRegister -> RegisterView(instance.component)
                is RootComponent.Child.VTCHome -> Unit
            }
        }
    }
}