package com.example.qualaboaapp.ui.theme.configuracoes

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "https://seu-endereco-de-api.com"

    fun getConfigurationsApi(): ConfiguracoesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ConfiguracoesApi::class.java)
    }
}
