package com.example.qualaboaapp.ui.theme.login

import LoginApi
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qualaboaapp.ui.theme.utils.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginApi: LoginApi,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _loginStatus = MutableStateFlow<Boolean?>(null)
    val loginStatus: StateFlow<Boolean?> = _loginStatus

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginError.value = "Preencha todos os campos"
            return
        }

        viewModelScope.launch {
            try {
                val response = loginApi.login(LoginRequest(email, password))

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val userName = responseBody?.get("usuario")?.toString() ?: ""
                    val token = responseBody?.get("token")?.toString() ?: ""

                    // Save user info in DataStore
                    userPreferences.saveUserInfo(isLoggedIn = true, userName = userName, email = email)
                    userPreferences.saveToken(token)

                    _loginStatus.value = true
                    _loginError.value = null
                } else {
                    _loginStatus.value = false
                    _loginError.value = response.errorBody()?.string()
                }
            } catch (e: Exception) {
                _loginStatus.value = false
                _loginError.value = e.message
            }
        }
    }
}
