package com.example.qualaboaapp.ui.theme.cadastro

import com.example.qualaboaapp.ui.theme.utils.NetworkUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

object RetrofitService {
    private const val BASE_URL = "https://qualaboa.servebeer.com/api/ms-auth/"

    // Utilize o OkHttpClient inseguro
    private val client: OkHttpClient = NetworkUtils.getUnsafeOkHttpClient()

    fun getCadastroApi(): CadastroApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CadastroApi::class.java)
    }
}
