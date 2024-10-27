package com.example.qualaboaapp.ui.theme.configuracoes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qualaboaapp.ui.theme.configuracoes.ConfiguracoesApi
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ConfiguracoesViewModel : ViewModel() {

    private val configuracoesApi: ConfiguracoesApi = RetrofitService.getConfigurationsApi()

    fun atualizarPerfil(nome: String, email: String, senha: String) {
        viewModelScope.launch {
            try {
                val response = configuracoesApi.atualizarPerfil(nome, email, senha)
                if (response.isSuccessful) {
                    // Lógica caso a atualização seja bem-sucedida
                } else {
                    // Tratar erro ao atualizar perfil
                }
            } catch (e: HttpException) {
                // Tratar erro de requisição
            } catch (e: Exception) {
                // Tratar outros tipos de erro
            }
        }
    }
}

