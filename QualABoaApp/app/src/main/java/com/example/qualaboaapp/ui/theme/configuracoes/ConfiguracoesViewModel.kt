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

    fun carregarDadosDoUsuario(userId: String?) {
        viewModelScope.launch {
            try {
                // Determina o ID do usuário
                val resolvedUserId = if (!userId.isNullOrBlank()) {
                    userId
                } else {
                    userPreferences.getUserId() ?: throw IllegalStateException("Nenhum ID de usuário encontrado.")
                }

                Log.d("ViewModel", "Carregando dados para o usuário com ID: $resolvedUserId")

                // Chamada à API para buscar os dados do usuário
                val response = configuracoesApi.getDadosUsuario(resolvedUserId)
                if (response.isSuccessful) {
                    response.body()?.let { dados ->
                        _nomeUsuario.value = dados.name ?: ""
                        _emailUsuario.value = dados.email ?: ""
                        Log.d("ViewModel", "Dados do usuário carregados: $dados")
                    }
                } else {
                    Log.e("ViewModel", "Erro ao carregar dados do usuário: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Erro ao carregar dados do usuário", e)
            }
        }
    }

    fun atualizarPerfil(name: String?, email: String?, password: String?) {
        viewModelScope.launch {
            try {
                // Resolve o ID do usuário
                val userId = _idUsuario ?: userPreferences.getUserId() ?: run {
                    Log.e("atualizarPerfil", "ID do usuário não encontrado. Abortando operação.")
                    return@launch
                }

                Log.d("atualizarPerfil", "Iniciando atualização de perfil com ID: $userId")

                // Criação do objeto de requisição
                val request = UsuarioData(
                    id = userId,
                    name = name.takeIf { !it.isNullOrBlank() },
                    email = email.takeIf { !it.isNullOrBlank() },
                    password = password.takeIf { !it.isNullOrBlank() }
                )

                Log.d("atualizarPerfil", "Request enviado: $request")

                // Chamada à API para atualizar o perfil
                val response = configuracoesApi.atualizarPerfil(userId, request)

                if (response.isSuccessful) {
                    response.body()?.let { dados ->
                        Log.d("atualizarPerfil", "Dados atualizados recebidos: $dados")

                        // Atualiza o estado interno e salva no UserPreferences
                        _nomeUsuario.value = dados.name ?: ""
                        _emailUsuario.value = dados.email ?: ""
                        userPreferences.saveUserInfo(true, dados.email, dados.name, dados.id)

                        Log.d("atualizarPerfil", "Dados salvos no UserPreferences com sucesso.")
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Resposta vazia"
                    Log.e("atualizarPerfil", "Erro ao atualizar perfil. Código HTTP: ${response.code()}, Erro: $errorBody")
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
