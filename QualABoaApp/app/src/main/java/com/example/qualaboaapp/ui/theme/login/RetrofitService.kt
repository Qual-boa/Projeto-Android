package com.example.qualaboaapp.ui.theme.login

import LoginApi
import com.example.qualaboaapp.ui.theme.cadastro.RetrofitService
import com.example.qualaboaapp.ui.theme.utils.NetworkUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "https://ec2-44-193-67-208.compute-1.amazonaws.com/api/ms-auth/"

    private val client: OkHttpClient = NetworkUtils.getUnsafeOkHttpClient()

    fun getLoginApi(): LoginApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // Adicione o cliente inseguro aqui
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi::class.java)
    }
}
