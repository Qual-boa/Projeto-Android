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
                .baseUrl("https://ec2-107-23-82-242.compute-1.amazonaws.com/api/ms-auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EstablishmentsApi::class.java)
        }
    }
}
