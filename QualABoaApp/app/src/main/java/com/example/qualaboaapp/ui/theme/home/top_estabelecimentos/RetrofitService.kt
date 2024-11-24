package com.example.qualaboaapp.ui.theme.home.top_estabelecimentos

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "http://44.206.188.183:8080/api/ms-auth/"
    private const val PHOTO_BASE_URL = "https://ec2-98-82-209-129.compute-1.amazonaws.com/api/ms-blob/"

    val establishmentsApi: EstablishmentsApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EstablishmentsApi::class.java)
    }

    val photoApi: EstablishmentPhotoApi by lazy {
        Retrofit.Builder()
            .baseUrl(PHOTO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EstablishmentPhotoApi::class.java)
    }
}
