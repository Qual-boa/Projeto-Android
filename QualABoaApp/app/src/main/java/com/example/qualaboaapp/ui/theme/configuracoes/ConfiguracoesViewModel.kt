import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qualaboaapp.ui.theme.configuracoes.UsuarioData
import com.example.qualaboaapp.ui.theme.utils.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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

    private val _isLoggedOut = MutableStateFlow(false)
    val isLoggedOut: StateFlow<Boolean> = _isLoggedOut

    init {
        carregarDadosUsuario()
    }

    /**
     * Carrega os dados do usuário do servidor e os salva localmente.
     */
    private fun carregarDadosUsuario() {
        viewModelScope.launch {
            try {
                val userId = userPreferences.getUserId() // Obtém o ID do usuário do UserPreferences
                if (userId != null) {
                    val response = configuracoesApi.getDadosUsuario(userId)
                    if (response.isSuccessful) {
                        response.body()?.let { dados ->
                            _idUsuario = dados.id
                            _nomeUsuario.value = dados.name ?: ""
                            _emailUsuario.value = dados.email ?: ""

                            // Salve os dados no UserPreferences
                            userPreferences.saveUserInfo(
                                isLoggedIn = true,
                                email = dados.email,
                                userName = dados.name
                            )
                        }
                    } else {
                        Log.e("ViewModel", "Erro ao carregar dados do usuário: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Erro ao carregar dados do usuário", e)
            }
        }
    }

    /**
     * Atualiza os dados do perfil do usuário no servidor.
     */
    fun atualizarPerfil(name: String?, email: String?, password: String?) {
        val id = _idUsuario ?: run {
            Log.e("atualizarPerfil", "ID do usuário é nulo. Abortando operação de atualização.")
            return
        }
        Log.d("atualizarPerfil", "Iniciando atualização de perfil com ID: $id")
        viewModelScope.launch {
            try {
                val request = UsuarioData(
                    id = id,
                    name = name.takeIf { !it.isNullOrBlank() },
                    email = email.takeIf { !it.isNullOrBlank() },
                    password = password.takeIf { !it.isNullOrBlank() }
                )
                Log.d("atualizarPerfil", "Request enviado: $request")

                val response = configuracoesApi.atualizarPerfil(id, request)
                Log.d("atualizarPerfil", "Resposta recebida: $response")

                if (response.isSuccessful) {
                    response.body()?.let { dados ->
                        Log.d("atualizarPerfil", "Dados recebidos: $dados")

                        _nomeUsuario.value = dados.name ?: ""
                        _emailUsuario.value = dados.email ?: ""
                        userPreferences.saveUserInfo(true, dados.email, dados.name)
                        Log.d("atualizarPerfil", "Dados salvos no UserPreferences com sucesso.")
                    }
                } else {
                    Log.e(
                        "atualizarPerfil",
                        "Erro ao atualizar perfil. Código HTTP: ${response.code()}, Erro: ${
                            response.errorBody()?.string() ?: "Resposta vazia"
                        }"
                    )
                }
            } catch (e: Exception) {
                Log.e("atualizarPerfil", "Exceção ao atualizar perfil", e)
            }
        }
    }



    /**
     * Atualiza o nome localmente.
     */
    fun atualizarNome(novoNome: String) {
        _nomeUsuario.value = novoNome
    }

    /**
     * Atualiza o email localmente.
     */
    fun atualizarEmail(novoEmail: String) {
        _emailUsuario.value = novoEmail
    }

    /**
     * Atualiza a senha localmente.
     */
    fun atualizarSenha(novaSenha: String) {
        _senhaUsuario.value = novaSenha
    }

    /**
     * Realiza o logout do usuário, limpa os dados locais e notifica a UI.
     */
    fun logout() {
        viewModelScope.launch {
            try {
                Log.d("logout", "Executando logout...")
                userPreferences.clearUserInfo()
                _nomeUsuario.value = ""
                _emailUsuario.value = ""
                _senhaUsuario.value = ""
                _idUsuario = null
                _isLoggedOut.value = true
                Log.d("logout", "Logout realizado com sucesso.")
            } catch (e: Exception) {
                Log.e("logout", "Erro ao realizar logout", e)
            }
        }
    }
}
