package com.example.qualaboaapp.viewmodel

import LoginApi
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qualaboaapp.ui.theme.login.LoginRequest
import com.example.qualaboaapp.ui.theme.login.RetrofitService
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel : ViewModel() {

    private val loginApi: LoginApi = RetrofitService.getLoginApi()

    // LiveData para status de login e mensagens de erro
    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> = _loginStatus

    private val _erroLogin = MutableLiveData<String?>()
    val erroLogin: LiveData<String?> = _erroLogin

    fun login(email: String, senha: String) {
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(email, senha)
                val response = loginApi.login(loginRequest)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val token = responseBody?.get("token")?.toString()
                    val userName = responseBody?.get("usuario")?.toString()

                    // Log de sucesso
                    Log.d("LoginSuccess", "Login bem-sucedido para o usuário: $userName")

                    _loginStatus.value = true
                    _erroLogin.value = null

                    // Aqui, você pode salvar o token localmente, se necessário

                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("LoginError", "Erro no login: $errorBody")

                    _loginStatus.value = false
                    _erroLogin.value = "Erro no login: $errorBody"
                }
            } catch (e: HttpException) {
                Log.e("LoginError", "Erro HTTP no login: ${e.message()}", e)
                _loginStatus.value = false
                _erroLogin.value = "Erro HTTP: ${e.message()}"
            } catch (e: Exception) {
                Log.e("LoginError", "Erro desconhecido no login: ${e.message}", e)
                _loginStatus.value = false
                _erroLogin.value = "Erro desconhecido: ${e.message}"
            }
        }
    }
}
