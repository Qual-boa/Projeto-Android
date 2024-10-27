package com.example.qualaboaapp.ui.theme.configuracoes

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ConfiguracoesApi {

    @FormUrlEncoded
    @POST("/perfil/atualizar")
    suspend fun atualizarPerfil(
        @Field("nome") nome: String,
        @Field("email") email: String,
        @Field("senha") senha: String
    ): Response<Void> // Você pode modificar isso conforme a resposta da API
}
