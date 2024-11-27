package com.example.qualaboaapp.ui.theme.home.categorias

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "https://ec2-54-84-26-145.compute-1.amazonaws.com/api/ms-auth/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}



