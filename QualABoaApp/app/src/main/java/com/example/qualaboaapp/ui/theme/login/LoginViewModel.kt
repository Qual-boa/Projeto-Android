package com.example.qualaboaapp.ui.theme.login

import LoginApi
import UserDetailsResponse
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

    private val _userDetails = MutableStateFlow<UserDetailsResponse?>(null)
    val userDetails: StateFlow<UserDetailsResponse?> = _userDetails

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
                    val token = responseBody?.get("token")?.toString() ?: ""

                    // Save user info in DataStore
                    userPreferences.saveToken(token)

                    // Chamar o endpoint users/byEmail
                    val userResponse = loginApi.getUserByEmail(email)

                    if (userResponse.isSuccessful) {
                        val userInfo = userResponse.body()

                        // Chamar o endpoint users/{id}
                        userInfo?.userId?.let { userId ->
                            val userDetailsResponse = loginApi.getUserDetails(userId)

                            if (userDetailsResponse.isSuccessful) {
                                _userDetails.value = userDetailsResponse.body()
                                userPreferences.saveUserInfo(
                                    isLoggedIn = true,
                                    userName = userDetailsResponse.body()?.name,
                                    email = userDetailsResponse.body()?.email,
                                    userId = userId
                                )
                            } else {
                                _loginError.value = userDetailsResponse.errorBody()?.string()
                            }
                        }
                    } else {
                        _loginError.value = userResponse.errorBody()?.string()
                    }

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