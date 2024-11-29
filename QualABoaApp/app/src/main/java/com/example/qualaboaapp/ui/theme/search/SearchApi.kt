package com.example.qualaboaapp.ui.theme.search

import com.example.qualaboaapp.ui.theme.establishment.AddressResponse
import com.example.qualaboaapp.ui.theme.establishment.EstablishmentResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("establishments/listbyfilters")
    suspend fun searchBars(@Body body: SearchRequest): List<BarResponse>

    @GET("establishments/{id}")
    suspend fun getEstablishmentById(@Path("id") id: String): EstablishmentResponse

    @GET("address/establishment/{establishmentId}")
    suspend fun getAddressByEstablishmentId(@Path("establishmentId") establishmentId: String): List<AddressResponse>

}

data class BarResponse(
    val id: String, // UUID como String
    val fantasyName: String,
    val cnpj: String,
    val averageOrderValue: Int,
    val categories: List<CategoryResponse>?,
    val relationships: List<RelationshipResponse>?,
    val information: InformationResponse
)

data class CategoryResponse(
    val categoryType: Int,
    val category: Int,
    val name: String,
    val searchesCount: Int
)

data class RelationshipResponse(
    val userId: String,
    val establishmentId: String,
    val interactionType: String,
    val rate: Double,
    val message: String
)

data class InformationResponse(
    val id: String,
    val hasParking: Boolean,
    val hasAccessibility: Boolean,
    val hasTv: Boolean,
    val hasWifi: Boolean,
    val openAt: List<Int>, // Exemplo: [12, 0]
    val closeAt: List<Int>, // Exemplo: [23, 0]
    val phone: String,
    val facebookUrl: String?,
    val instagramUrl: String?,
    val telegramUrl: String?,
    val description: String
)


