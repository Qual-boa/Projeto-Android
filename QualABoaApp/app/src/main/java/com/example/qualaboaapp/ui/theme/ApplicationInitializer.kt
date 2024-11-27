package com.example.qualaboaapp.ui.theme

import android.app.Application
import cadastroModule
import com.example.qualaboaapp.ui.theme.home.categorias.categoriesModule
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.establishmentsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.example.qualaboaapp.ui.theme.login.loginModule
import com.example.qualaboaapp.ui.theme.search.searchModule

class ApplicationInitializer  : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ApplicationInitializer)
            modules(
                listOf(
                    cadastroModule, // Your Cadastro module
                    loginModule,
                    categoriesModule,
                    establishmentsModule,// Your Login module
                    searchModule
                )
            )
        }
    }
}