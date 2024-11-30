import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "https://qualaboa.servebeer.com/api/ms-auth/"

    fun provideConfigurationsApi(): ConfiguracoesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ConfiguracoesApi::class.java)
    }
}


