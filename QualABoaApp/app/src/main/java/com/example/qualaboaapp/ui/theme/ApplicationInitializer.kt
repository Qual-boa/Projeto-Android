package com.example.qualaboaapp.ui.theme

import android.app.Application
import cadastroModule
import com.example.qualaboaapp.ui.theme.home.categorias.categoriesModule
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.establishmentsModule
import com.example.qualaboaapp.ui.theme.login.loginModule
import com.example.qualaboaapp.ui.theme.search.searchModule
import favoritesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ApplicationInitializer : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ApplicationInitializer)
            modules(
                listOf(
                    cadastroModule,
                    loginModule,
                    categoriesModule,
                    establishmentsModule,
                    searchModule,
                    favoritesModule
                )
            )
        }
    }
}
