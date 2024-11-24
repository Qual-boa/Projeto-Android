package com.example.qualaboaapp.ui.theme.home.top_estabelecimentos

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface EstablishmentPhotoApi {
    @GET("blob/establishments/{establishmentId}")
    suspend fun getEstablishmentPhotos(@Path("establishmentId") establishmentId: String): List<String>

    companion object {
        fun create(): EstablishmentPhotoApi {
            return Retrofit.Builder()
                .baseUrl("https://ec2-35-175-9-58.compute-1.amazonaws.com/api/ms-blob/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EstablishmentPhotoApi::class.java)
        }
    }
}
