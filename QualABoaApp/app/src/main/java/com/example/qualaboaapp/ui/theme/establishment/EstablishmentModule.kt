package com.example.qualaboaapp.ui.theme.establishment

import com.example.qualaboaapp.ui.theme.search.BarRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val establishmentModule = module {
    single { BarRepository(androidContext()) } // Registro do repository
    viewModel { EstablishmentViewModel(get()) } // Passa o repository para o ViewModel
}
