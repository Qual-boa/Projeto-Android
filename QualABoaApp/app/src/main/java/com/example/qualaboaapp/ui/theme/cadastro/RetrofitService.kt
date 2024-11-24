package com.example.qualaboaapp.ui.theme.cadastro

import com.example.qualaboaapp.ui.theme.utils.NetworkUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

object RetrofitService {
    private const val BASE_URL = "http://44.206.188.183:8080/api/ms-auth/"

    // Utilize o OkHttpClient inseguro
    private val client: OkHttpClient = NetworkUtils.getUnsafeOkHttpClient()

    fun getCadastroApi(): CadastroApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // Adicione o cliente inseguro aqui
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CadastroApi::class.java)
    }
}
