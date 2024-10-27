package com.example.qualaboaapp.ui.theme.cadastro

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

import javax.net.ssl.SSLPeerUnverifiedException

class CadastroViewModel : ViewModel() {

    private val cadastroApi: CadastroApi = RetrofitService.getCadastroApi()

    private val _cadastroStatus = MutableLiveData<Boolean>()
    val cadastroStatus: LiveData<Boolean> = _cadastroStatus

    private val _erroCadastro = MutableLiveData<String?>()
    val erroCadastro: LiveData<String?> = _erroCadastro

    fun cadastrarUsuario(name: String, email: String, password: String) {
        val usuario = Usuario(name, email, password)
        viewModelScope.launch {
            try {
                val response = cadastroApi.cadastrarUsuario(usuario)
                if (response.isSuccessful) {
                    _cadastroStatus.value = true
                    _erroCadastro.value = null
                } else {
                    _cadastroStatus.value = false
                    val errorBody = response.errorBody()?.string()
                    _erroCadastro.value = errorBody
                    Log.e("CadastroError", "Erro no cadastro: $errorBody")
                }
            } catch (e: SSLPeerUnverifiedException) {
                _cadastroStatus.value = false
                _erroCadastro.value = "Erro SSL: Verificação de certificado falhou."
                Log.e("CadastroError", "Erro SSL no cadastro", e)
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
