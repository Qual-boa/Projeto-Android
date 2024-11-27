import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qualaboaapp.ui.theme.configuracoes.UsuarioData
import com.example.qualaboaapp.ui.theme.utils.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ConfiguracoesViewModel(
    private val userPreferences: UserPreferences,
    private val configuracoesApi: ConfiguracoesApi
) : ViewModel() {

    private val _nomeUsuario = MutableStateFlow("")
    val nomeUsuario: StateFlow<String> = _nomeUsuario

    private val _emailUsuario = MutableStateFlow("")
    val emailUsuario: StateFlow<String> = _emailUsuario

    private val _senhaUsuario = MutableStateFlow("")
    val senhaUsuario: StateFlow<String> = _senhaUsuario

    private var _idUsuario: String? = null

    init {
        carregarDadosUsuario()
    }

    private fun carregarDadosUsuario() {
        viewModelScope.launch {
            try {
                val response = configuracoesApi.getDadosUsuario()
                if (response.isSuccessful) {
                    response.body()?.let { dados ->
                        _idUsuario = dados.id
                        _nomeUsuario.value = dados.name
                        _emailUsuario.value = dados.email
                        _senhaUsuario.value = "" // Senha não deve ser exibida por questões de segurança
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun atualizarPerfil(name: String, email: String, password: String) {
        val id = _idUsuario ?: return
        viewModelScope.launch {
            try {
                val request = UsuarioData(id = id, name = name, email = email, password = password)
                val response = configuracoesApi.atualizarPerfil(id, request)
                if (response.isSuccessful) {
                    _nomeUsuario.value = name
                    _emailUsuario.value = email
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun atualizarNome(novoNome: String) {
        _nomeUsuario.value = novoNome
    }

    fun atualizarEmail(novoEmail: String) {
        _emailUsuario.value = novoEmail
    }

    fun atualizarSenha(novaSenha: String) {
        _senhaUsuario.value = novaSenha
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.clearUserInfo()
            _nomeUsuario.value = ""
            _emailUsuario.value = ""
            _senhaUsuario.value = ""
            _idUsuario = null
        }
    }
}
