package com.lab.vtc.app.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

interface MainComponent {
    fun initApp(isRegistered: Boolean)
}

class DefaultMainComponent (
    context: ComponentContext,
    private val onFinished: (isRegistered: Boolean) -> Unit
) : MainComponent, ComponentContext by context {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun initApp(isRegistered: Boolean) {
        lifecycle.doOnDestroy { scope.cancel() }

        scope.launch {
            onFinished(isRegistered)
        }
    }
}