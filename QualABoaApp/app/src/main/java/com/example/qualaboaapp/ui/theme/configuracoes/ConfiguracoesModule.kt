package com.example.qualaboaapp.ui.theme.configuracoes

import ConfiguracoesViewModel
import RetrofitService.provideConfigurationsApi
import com.example.qualaboaapp.ui.theme.utils.UserPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val configuracoesModule = module {
    single { provideConfigurationsApi() } // Retrofit API
    single { UserPreferences(androidContext()) } // User Preferences
    viewModel { ConfiguracoesViewModel(get(), get()) } // ViewModel
}
