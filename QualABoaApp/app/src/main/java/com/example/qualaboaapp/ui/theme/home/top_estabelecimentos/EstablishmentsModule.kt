package com.example.qualaboaapp.ui.theme.home.top_estabelecimentos

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val establishmentsModule = module {
    single { EstablishmentsRepository(get(), get()) } // Repositório
    single { EstablishmentsApi.create() } // API de Estabelecimentos
    single { EstablishmentPhotoApi.create() } // API de Fotos
    viewModel { EstablishmentsViewModel(get()) } // ViewModel
}
