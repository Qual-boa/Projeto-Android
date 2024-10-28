package com.example.qualaboaapp.ui.theme.cadastro

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CadastroApi {
    @POST("users/register")
    suspend fun cadastrarUsuario(@Body usuario: UsuarioRequest): Response<UsuarioResponse>
}
