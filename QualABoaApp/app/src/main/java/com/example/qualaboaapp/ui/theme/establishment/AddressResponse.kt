package com.example.qualaboaapp.ui.theme.establishment

data class AddressResponse(
    val id: String,
    val street: String,
    val number: String,
    val postalCode: String,
    val neighborhood: String,
    val complement: String?,
    val state: String,
    val city: String
)


