package com.example.qualaboaapp.ui.theme.cadastro

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qualaboaapp.ui.theme.cadastro.CadastroApi
import com.example.qualaboaapp.ui.theme.cadastro.UsuarioRequest
import com.example.qualaboaapp.ui.theme.utils.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CadastroViewModel(
    private val cadastroApi: CadastroApi,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _cadastroStatus = MutableStateFlow<Boolean?>(null)
    val cadastroStatus: StateFlow<Boolean?> = _cadastroStatus

    private val _erroCadastro = MutableStateFlow<String?>(null)
    val erroCadastro: StateFlow<String?> = _erroCadastro

    fun cadastrarUsuario(email: String?, nome: String?, senha: String?) {
        if (nome.isNullOrBlank() || email.isNullOrBlank() || senha.isNullOrBlank()) {
            _erroCadastro.value = "Preencha todos os campos"
            return
        }

        viewModelScope.launch {
            try {
                val usuario = UsuarioRequest(email, nome, senha)
                val response = cadastroApi.cadastrarUsuario(usuario)

                if (response.isSuccessful) {
                    _cadastroStatus.value = true
                    _erroCadastro.value = null
                    userPreferences.saveUserInfo(true, email, nome)
                } else {
                    _cadastroStatus.value = false
                    _erroCadastro.value = response.errorBody()?.string()
                }
            } catch (e: Exception) {
                _cadastroStatus.value = false
                _erroCadastro.value = e.message
            }
        }
    }
}
