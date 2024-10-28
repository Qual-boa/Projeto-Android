package com.example.qualaboaapp.ui.theme.configuracoes

import ConfiguracoesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "https://ec2-44-193-67-208.compute-1.amazonaws.com/api/ms-auth/"

    fun getConfigurationsApi(): ConfiguracoesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ConfiguracoesApi::class.java)
    }
}
