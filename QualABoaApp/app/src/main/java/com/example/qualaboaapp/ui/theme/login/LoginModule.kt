package com.example.qualaboaapp.ui.theme.login

import com.example.qualaboaapp.ui.theme.utils.UserPreferences
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.qualaboaapp.ui.theme.login.LoginViewModel

val loginModule = module {
    single { RetrofitService.getLoginApi() } // Provide LoginApi
    single { UserPreferences(get()) } // Provide UserPreferences
    viewModel { LoginViewModel(get(), get()) } // Provide LoginViewModel
}
