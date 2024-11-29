import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.Establishment
import retrofit2.http.GET
import retrofit2.http.Path

interface FavoriteApi {
    @GET("establishments/favorites/{userId}")
    suspend fun getFavorites(@Path("userId") userId: String): List<Establishment>
}
