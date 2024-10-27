package com.example.qualaboaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qualaboaapp.ui.theme.login.LoginApi
import com.example.qualaboaapp.ui.theme.login.RetrofitService
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel : ViewModel() {

    private val loginApi: LoginApi = RetrofitService.getLoginApi()

    fun login(email: String, senha: String) {
        viewModelScope.launch {
            try {
                val response = loginApi.login(email, senha)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    // Manipule a resposta diretamente do Map
                    val token = responseBody?.get("token")?.toString()
                    val userName = responseBody?.get("usuario")?.toString()

                    // Aqui você pode tratar os dados como necessário, como salvar o token
                } else {
                    // Trate o erro de login, como mostrar uma mensagem de erro
                }
            } catch (e: HttpException) {
                // Trate o erro de requisição
            } catch (e: Exception) {
                // Trate outros tipos de erro
            }
        }
    }
}
