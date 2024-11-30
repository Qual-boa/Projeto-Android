package com.example.qualaboaapp.ui.theme.home.categorias

import android.content.Context

class CategoriaRepository(context: Context) {

    private val api = RetrofitService.provideRetrofit(context).create(CategoriaApi::class.java)

    suspend fun getCategorias() = api.getCategorias()
}
