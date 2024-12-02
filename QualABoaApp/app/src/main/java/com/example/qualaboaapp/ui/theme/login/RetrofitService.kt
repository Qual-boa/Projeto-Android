package com.example.qualaboaapp.ui.theme.login

import LoginApi
import com.example.qualaboaapp.ui.theme.cadastro.RetrofitService
import com.example.qualaboaapp.ui.theme.utils.NetworkUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "https://qualaboa.servebeer.com/api/ms-auth/"

    private val client: OkHttpClient = NetworkUtils.getUnsafeOkHttpClient()

    fun getLoginApi(): LoginApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi::class.java)
    }
}
