package com.example.qualaboaapp.ui.theme

import android.app.Application
import cadastroModule
import com.example.qualaboaapp.ui.theme.establishment.establishmentModule
import com.example.qualaboaapp.ui.theme.configuracoes.configuracoesModule
import com.example.qualaboaapp.ui.theme.home.categorias.categoriesModule
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.establishmentsModule
import com.example.qualaboaapp.ui.theme.home.userPreferencesModule
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
                    establishmentModule,
                    searchModule,
                    establishmentsModule,
                    userPreferencesModule,
                    configuracoesModule// Your Login module
                )
            )
        }
    }
}