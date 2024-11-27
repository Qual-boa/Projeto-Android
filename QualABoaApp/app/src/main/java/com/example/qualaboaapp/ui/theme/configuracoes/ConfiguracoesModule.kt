package com.example.qualaboaapp.ui.theme.configuracoes

import ConfiguracoesViewModel
import RetrofitService.provideConfigurationsApi
import com.example.qualaboaapp.ui.theme.utils.UserPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val configuracoesModule = module {
    single { provideConfigurationsApi() }
    single { ConfiguracoesViewModel(get(), get()) }
    single { UserPreferences(androidContext()) }
}