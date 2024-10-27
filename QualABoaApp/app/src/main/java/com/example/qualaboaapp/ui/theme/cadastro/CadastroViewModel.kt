package com.example.qualaboaapp.ui.theme.cadastro

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CadastroViewModel : ViewModel() {

    private val cadastroApi: CadastroApi = RetrofitService.getCadastroApi()

    private val _cadastroStatus = MutableLiveData<Boolean>()
    val cadastroStatus: LiveData<Boolean> = _cadastroStatus

    private val _erroCadastro = MutableLiveData<String?>()
    val erroCadastro: LiveData<String?> = _erroCadastro

    fun cadastrarUsuario(email: String, name: String, password: String) {
        val usuario = Usuario(email, name, password)  // roleEnum como "USER" e establishmentId como null
        viewModelScope.launch {
            try {
                val response = cadastroApi.cadastrarUsuario(usuario)
                if (response.isSuccessful) {
                    _cadastroStatus.value = true
                    _erroCadastro.value = null  // Limpa qualquer erro anterior
                } else {
                    _cadastroStatus.value = false
                    val errorBody = response.errorBody()?.string()
                    _erroCadastro.value = errorBody // Define a mensagem de erro para exibir na UI
                    Log.e("CadastroError", "Erro no cadastro: $errorBody")  // Loga o erro no console
                }
            } catch (e: HttpException) {
                _cadastroStatus.value = false
                _erroCadastro.value = "Erro HTTP: ${e.message()}"
                Log.e("CadastroError", "Erro HTTP no cadastro", e)
            } catch (e: Exception) {
                _cadastroStatus.value = false
                _erroCadastro.value = "Erro desconhecido: ${e.message}"
                Log.e("CadastroError", "Erro desconhecido no cadastro", e)
            }
        }
    }
}
