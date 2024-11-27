package com.example.qualaboaapp.ui.theme.home.categorias

import retrofit2.http.GET


interface CategoriaApi {
    @GET("categories")
    suspend fun getCategorias(): List<Category>
}

