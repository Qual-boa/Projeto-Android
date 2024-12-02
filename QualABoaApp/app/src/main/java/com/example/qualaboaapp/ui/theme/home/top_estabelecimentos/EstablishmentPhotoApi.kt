package com.example.qualaboaapp.ui.theme.home.top_estabelecimentos

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// Representação dos dados retornados pela API
data class ApiEstablishmentPhoto(
    val id: Int,
    val establishmentId: String,
    val establishmentCategory: String,
    val imgUrl: String,
    val originalFilename: String
)

interface EstablishmentPhotoApi {
    @GET("blob/establishments/{establishmentId}")
    suspend fun getEstablishmentPhotos(@Path("establishmentId") establishmentId: String): List<ApiEstablishmentPhoto>

    companion object {
        fun create(context: Context): EstablishmentPhotoApi {
            val client = RetrofitService.createSecureOkHttpClient(context)
            return Retrofit.Builder()
                .baseUrl("https://ec2-35-175-9-58.compute-1.amazonaws.com/api/ms-blob/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EstablishmentPhotoApi::class.java)
        }
    }
}
