package com.example.qualaboaapp.ui.theme.home.top_estabelecimentos

data class Establishment(
    val id: String,
    val fantasyName: String,
    val cnpj: String?,
    val averageOrderValue: Float,
    val categories: List<Category>?,
    val relationships: List<Relationship>?,
    val information: Information?
)

data class Category(
    val categoryType: Int,
    val category: Int,
    val name: String,
    val searchesCount: Int
)

data class Relationship(
    val userId: String,
    val establishmentId: String,
    val interactionType: String,
    val rate: Float,
    val message: String?
)

data class Information(
    val id: String,
    val hasParking: Boolean,
    val hasAccessibility: Boolean,
    val hasTv: Boolean,
    val hasWifi: Boolean,
    val openAt: List<Int>?, // Alterado para uma lista de inteiros
    val closeAt: List<Int>?, // Alterado para uma lista de inteiros
    val phone: String?,
    val facebookUrl: String?,
    val instagramUrl: String?,
    val telegramUrl: String?,
    val description: String?
)

data class Time(
    val hour: Int,
    val minute: Int,
    val second: Int,
    val nano: Int
)

data class EstablishmentPhoto(
    val id: Int,
    val establishmentId: String,
    val establishmentCategory: String,
    val imgUrl: String,
    val originalFilename: String
)
