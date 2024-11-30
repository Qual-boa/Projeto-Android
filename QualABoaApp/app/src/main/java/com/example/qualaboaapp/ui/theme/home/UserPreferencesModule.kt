package com.example.qualaboaapp.ui.theme.home

import com.example.qualaboaapp.ui.theme.utils.UserPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val userPreferencesModule = module {
    single { UserPreferences(androidContext()) }
}