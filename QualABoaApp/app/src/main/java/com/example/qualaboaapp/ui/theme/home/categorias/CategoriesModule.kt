package com.example.qualaboaapp.ui.theme.home.categorias

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoriesModule = module {
    single { CategoriaRepository() } // Registro do repository
    viewModel { CategoriesViewModel(get()) } // Passa o repository para o ViewModel
}

