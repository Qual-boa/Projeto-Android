import android.content.SharedPreferences
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.androidx.viewmodel.dsl.viewModel

val favoritesModule = module {

    // Configuração do Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://suaapi.com") // Substitua pela URL real da API
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Criação do FavoriteApi
    single { get<Retrofit>().create(FavoriteApi::class.java) }

    // Função para verificar o estado de login do usuário usando SharedPreferences
    single { { get<SharedPreferences>().getBoolean("isLoggedIn", false) } }

    // Criação do ViewModel FavoritesViewModel com suas dependências
    viewModel { FavoritesViewModel(get(), get()) }
}
