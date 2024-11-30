import com.example.qualaboaapp.ui.theme.configuracoes.UsuarioData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ConfiguracoesApi {

    @PUT("/users/{id}")
    suspend fun atualizarPerfil(
        @Path("id") id: String,
        @Body perfilRequest: UsuarioData
    ): Response<UsuarioData>

    @GET("/users/{id}")
    suspend fun getDadosUsuario(
        @Path("id") id: String
    ): Response<UsuarioData>

}
