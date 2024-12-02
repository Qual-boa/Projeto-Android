import com.example.qualaboaapp.ui.theme.login.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LoginApi {
    @Headers("Content-Type: application/json")
    @POST("users/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<Map<String, Any>>

    @Headers("Content-Type: application/json")
    @GET("users/byEmail")
    suspend fun getUserByEmail(@Query("email") email: String): Response<UserResponse>

    @Headers("Content-Type: application/json")
    @GET("users/{id}")
    suspend fun getUserDetails(@Path("id") id: String): Response<UserDetailsResponse>
}

data class UserResponse(
    val userId: String,
    val establishmentId: String
)

data class UserDetailsResponse(
    val id: String,
    val name: String,
    val email: String,
    val roleEnum: String
)
