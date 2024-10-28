import com.example.qualaboaapp.ui.theme.login.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApi {
    @Headers("Content-Type: application/json")
    @POST("users/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<Map<String, Any>>
}
