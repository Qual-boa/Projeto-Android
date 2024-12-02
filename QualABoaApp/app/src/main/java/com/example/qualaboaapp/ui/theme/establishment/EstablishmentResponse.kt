package com.example.qualaboaapp.ui.theme.establishment

data class EstablishmentResponse(
    val id: String,
    val fantasyName: String,
    val cnpj: String,
    val averageOrderValue: Int,
    val categories: List<Category>,
    val relationships: List<Relationship>,
    val information: Information
)

data class Category(val categoryType: Int, val category: Int, val name: String, val searchesCount: Int)
data class Relationship(val userId: String, val establishmentId: String, val interactionType: String, val rate: Double, val message: String)
data class Information(
    val id: String,
    val hasParking: Boolean,
    val hasAccessibility: Boolean,
    val hasTv: Boolean,
    val hasWifi: Boolean,
    val openAt: List<Int>,
    val closeAt: List<Int>,
    val phone: String,
    val facebookUrl: String?,
    val instagramUrl: String?,
    val telegramUrl: String?,
    val description: String
)

