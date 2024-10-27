package com.example.qualaboaapp.ui.theme.cadastro

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "https://ec2-3-83-64-26.compute-1.amazonaws.com/api/ms-auth/"

    fun getCadastroApi(): CadastroApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CadastroApi::class.java)
    }
}
