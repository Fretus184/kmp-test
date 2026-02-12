package com.lab.vtc.app.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

@Serializable
sealed class RootConfig {
    @Serializable object VTCHome: RootConfig()
    @Serializable object VTCRegister: RootConfig()
    @Serializable object VTCMain: RootConfig()
}

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>
    sealed class Child {
        class VTCHome(): Child()
        class VTCRegister(val component: RegisterComponent): Child()
        class VTCMain(val component: MainComponent): Child()
    }
}

class DefaultRootComponent(context: ComponentContext): RootComponent, ComponentContext by context {
    private val navigation = StackNavigation<RootConfig>()

    override val stack: Value<ChildStack<*, RootComponent.Child>>
        get() = childStack(
            source = navigation,
            serializer = serializer<RootConfig>(),
            initialConfiguration = RootConfig.VTCMain,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(config: RootConfig, context: ComponentContext) : RootComponent.Child =
        when (config) {
            is RootConfig.VTCMain -> RootComponent.Child.VTCMain(
                DefaultMainComponent(context) { isRegister ->
                    if (isRegister) navigation.replaceAll(RootConfig.VTCMain)
                    else navigation.replaceAll(RootConfig.VTCRegister)
                }
            )
            is RootConfig.VTCRegister -> RootComponent.Child.VTCRegister(
                DefaultRegisterComponent(context) { navigation.replaceAll(RootConfig.VTCHome) }
            )

            is RootConfig.VTCHome -> RootComponent.Child.VTCHome()
        }
}