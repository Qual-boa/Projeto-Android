package com.example.qualaboaapp.ui.theme.home.top_estabelecimentos

data class Establishment(
    val id: String, // Adicione o ID do estabelecimento
    val name: String,
    val rating: Float
)

data class EstablishmentPhoto(
    val id: Int,
    val establishmentId: String,
    val imgUrl: String
)
