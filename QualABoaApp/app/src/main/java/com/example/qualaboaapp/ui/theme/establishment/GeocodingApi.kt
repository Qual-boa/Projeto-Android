package com.example.qualaboaapp.ui.theme.establishment

import retrofit2.http.GET
import retrofit2.http.Url

interface GeocodingApi {
    @GET
    suspend fun getCoordinates(@Url url: String): GeocodingResponse
}
