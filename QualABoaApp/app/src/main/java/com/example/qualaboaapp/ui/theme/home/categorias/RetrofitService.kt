package com.example.qualaboaapp.ui.theme.home.categorias

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "http://44.206.188.183:8080/api/ms-auth/"

    // Configuração do cliente OkHttp com o Interceptor de Logging
    private val client: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            android.util.Log.d("HTTP_LOG", message) // Exibe os logs no Logcat
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY // Nível de logging (BODY registra tudo)
        }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Adiciona o interceptor de logging
            .build()
    }

    // Retrofit configurado com o cliente que inclui o interceptor
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // Inclui o OkHttpClient com o interceptor no Retrofit
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
