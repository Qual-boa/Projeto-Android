package com.example.qualaboaapp.ui.theme.home.categorias

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoriesModule = module {
    single { RetrofitService.provideRetrofit(androidContext())}
    single { CategoriaRepository(get()) }
    viewModel { CategoriesViewModel(get(), get()) }
}

