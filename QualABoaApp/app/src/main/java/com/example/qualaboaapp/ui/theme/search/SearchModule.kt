package com.example.qualaboaapp.ui.theme.search

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    single { BarRepository(androidContext()) } // Registro do repository
    viewModel { BarViewModel(get()) } // Passa o repository para o ViewModel
}
