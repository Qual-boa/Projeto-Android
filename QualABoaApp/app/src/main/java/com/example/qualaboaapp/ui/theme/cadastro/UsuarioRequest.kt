package com.example.qualaboaapp.ui.theme.cadastro

data class UsuarioRequest(
    val name: String,
    val email: String,
    val password: String,
    val roleEnum: String = "USER",
    val establishmentId: String? = null
)
