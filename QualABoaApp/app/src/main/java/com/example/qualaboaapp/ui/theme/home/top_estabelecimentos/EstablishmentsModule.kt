package com.example.qualaboaapp.ui.theme.home.top_estabelecimentos

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val establishmentsModule = module {
    single { RetrofitService.provideEstablishmentsApi(androidContext()) } // API de Estabelecimentos
    single { RetrofitService.providePhotoApi(androidContext()) } // API de Fotos
    single { EstablishmentsRepository(get(), get()) } // Repositório
    viewModel { EstablishmentsViewModel(get(), get())  } // ViewModel
}
