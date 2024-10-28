import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qualaboaapp.ui.theme.configuracoes.RetrofitService
import com.example.qualaboaapp.ui.theme.configuracoes.UsuarioData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ConfiguracoesViewModel : ViewModel() {

    private val configuracoesApi: ConfiguracoesApi = RetrofitService.getConfigurationsApi()

    private val _nomeUsuario = MutableStateFlow("")
    val nomeUsuario: StateFlow<String> = _nomeUsuario

    private val _emailUsuario = MutableStateFlow("")
    val emailUsuario: StateFlow<String> = _emailUsuario

    private val _senhaUsuario = MutableStateFlow("")
    val senhaUsuario: StateFlow<String> = _senhaUsuario

    private var _idUsuario: String? = null // Armazena o ID do usuário para uso posterior

    init {
        carregarDadosUsuario()
    }

    private fun carregarDadosUsuario() {
        viewModelScope.launch {
            try {
                val response = configuracoesApi.getDadosUsuario()
                if (response.isSuccessful) {
                    response.body()?.let { dados: UsuarioData ->
                        _idUsuario = dados.id
                        _nomeUsuario.value = dados.name
                        _emailUsuario.value = dados.email
                        _senhaUsuario.value = dados.password
                    }
                } else {
                    // Tratar erro ao carregar dados do usuário
                }
            } catch (e: HttpException) {
                // Tratar erro de requisição
            } catch (e: Exception) {
                // Tratar outros tipos de erro
            }
        }
    }

    fun atualizarPerfil(name: String, email: String, password: String) {
        val id = _idUsuario ?: return // Verifica se o ID do usuário foi carregado

        viewModelScope.launch {
            try {
                val perfilRequest = UsuarioData(id = id, name = name, email = email, password = password)
                val response = configuracoesApi.atualizarPerfil(id, perfilRequest)
                if (response.isSuccessful) {
                    _nomeUsuario.value = name
                    _emailUsuario.value = email
                    _senhaUsuario.value = password
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

    fun logout() {
        // Limpa os dados de sessão do usuário
        _nomeUsuario.value = ""
        _emailUsuario.value = ""
        _senhaUsuario.value = ""
        _idUsuario = null
    }
}
