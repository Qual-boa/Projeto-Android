package com.example.qualaboaapp.ui.theme.cadastro

data class UsuarioRequest(
    val email: String,
    val name: String,
    val password: String,
    val roleEnum: String = "USER",
    val establishmentId: String? = null
)
