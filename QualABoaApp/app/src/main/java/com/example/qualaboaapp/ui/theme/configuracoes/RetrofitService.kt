import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "https://ec2-34-235-31-164.compute-1.amazonaws.com//pi/ms-auth/"

    fun provideConfigurationsApi(): ConfiguracoesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ConfiguracoesApi::class.java)
    }
}


