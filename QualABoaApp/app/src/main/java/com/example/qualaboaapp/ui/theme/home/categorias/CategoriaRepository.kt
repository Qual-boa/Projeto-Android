package com.example.qualaboaapp.ui.theme.home.categorias


class CategoriaRepository {
    private val api = RetrofitService.retrofit.create(CategoriaApi::class.java)

    suspend fun getCategorias() = api.getCategorias()
}