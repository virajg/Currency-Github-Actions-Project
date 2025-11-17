package org.example.cmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.example.cmp.data.di.dataModules
import org.example.cmp.di.androidPlatformModule
import org.example.cmp.domain.di.domainModule
import org.example.cmp.presentation.App
import org.example.cmp.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        startKoin {
            androidContext(this@MainActivity)
            modules(
                androidPlatformModule,
                dataModules,
                domainModule,
                presentationModule
            )
        }

        super.onCreate(savedInstanceState)


        setContent {
            App()
        }
    }
}