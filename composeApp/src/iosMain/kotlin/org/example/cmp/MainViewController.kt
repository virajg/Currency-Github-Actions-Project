package org.example.cmp

import androidx.compose.ui.window.ComposeUIViewController
import org.example.cmp.data.di.dataModules
import org.example.cmp.di.iosPlatformModule
import org.example.cmp.domain.di.domainModule
import org.example.cmp.presentation.App
import org.example.cmp.presentation.di.presentationModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController {
    startKoin {
        modules(iosPlatformModule,dataModules, domainModule, presentationModule)  // dataModule is common
    }
    App()
}