import com.example.qualaboaapp.ui.theme.utils.UserPreferences
import com.example.qualaboaapp.ui.theme.cadastro.CadastroApi
import com.example.qualaboaapp.ui.theme.cadastro.CadastroViewModel
import com.example.qualaboaapp.ui.theme.cadastro.RetrofitService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cadastroModule = module {
    // Registro do CadastroApi
    single { RetrofitService.getCadastroApi() }

    // Registro do UserPreferences
    single { UserPreferences(get()) }

    // Registro do ViewModel com os dois parâmetros
    viewModel { CadastroViewModel(get(), get()) }
}
