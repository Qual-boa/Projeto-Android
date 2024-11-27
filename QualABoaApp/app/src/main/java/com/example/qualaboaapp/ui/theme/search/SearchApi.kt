package com.example.qualaboaapp.ui.theme.search

import retrofit2.http.Body
import retrofit2.http.POST

data class BarResponse(
    val id: Int,
    val name: String,
    val description: String,
    val additionalInfo: String,
    val imageUrl: String,
    val isFavorite: Boolean
)

interface ApiService {
    @POST("/establishments/listbyfilters") // Substitua pela rota correta
    suspend fun searchBars(@Body requestBody: Map<String, String>): List<BarResponse>
}

