package com.example.qualaboaapp.ui.theme.home.top_estabelecimentos

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface EstablishmentsApi {
    @GET("establishments")
    suspend fun getEstablishments(): List<Establishment>

    companion object {
        fun create(): EstablishmentsApi {
            return Retrofit.Builder()
                .baseUrl("http://44.206.188.183:8080/api/ms-auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EstablishmentsApi::class.java)
        }
    }
}
