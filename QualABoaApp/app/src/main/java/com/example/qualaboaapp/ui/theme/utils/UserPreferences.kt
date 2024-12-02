package com.example.qualaboaapp.ui.theme.utils

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.qualaboaapp.ui.theme.home.categorias.Category
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.Establishment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

// Define o DataStore
private val Context.dataStore by preferencesDataStore("user_preferences")

class UserPreferences(private val context: Context) {

    companion object {
        private val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val CATEGORIES_KEY = stringPreferencesKey("categories") // Para salvar categorias
        private val ESTABLISHMENTS_KEY = stringPreferencesKey("establishments") // Para salvar estabelecimentos
        private val USER_ID_KEY = stringPreferencesKey("user_id") // Chave para o ID do usuário
        private val TOKEN_KEY = stringPreferencesKey("auth_token") // Chave para o token de autenticação
    }

    private val gson = Gson() // Instância do Gson para conversões JSON

    // Fluxo para saber se o usuário está logado
    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN_KEY] ?: false
    }

    // Fluxo para obter o nome do usuário logado
    val userName: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_NAME_KEY]
    }

    // Fluxo para obter o email do usuário logado
    val userEmail: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_EMAIL_KEY]
    }

    // Fluxo para obter categorias salvas
    val categories: Flow<List<Category>> = context.dataStore.data.map { preferences ->
        preferences[CATEGORIES_KEY]?.let { json -> json.fromJsonToList<Category>() } ?: emptyList()
    }

    // Fluxo para obter estabelecimentos salvos
    val establishments: Flow<List<Establishment>> = context.dataStore.data.map { preferences ->
        preferences[ESTABLISHMENTS_KEY]?.let { json -> json.fromJsonToList<Establishment>() } ?: emptyList()
    }

    // Salvar o ID do usuário no DataStore
    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    // Recuperar o ID do usuário do DataStore
    suspend fun getUserId(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[USER_ID_KEY]
        }.firstOrNull()
    }

    // Salvar o token de autenticação
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    // Recuperar o token de autenticação
    suspend fun getToken(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }.firstOrNull()
    }

    /**
     * Salva informações do usuário no DataStore.
     */
    suspend fun saveUserInfo(isLoggedIn: Boolean, email: String?, userName: String?, userId: String?) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN_KEY] = isLoggedIn
            preferences[USER_EMAIL_KEY] = email ?: ""
            preferences[USER_NAME_KEY] = userName ?: ""
            preferences[USER_ID_KEY] = userId ?: ""
        }
    }

    // Recuperar informações do usuário
    suspend fun getUserInfo(): Pair<String?, String?> {
        val preferences = context.dataStore.data.first()
        val email = preferences[USER_EMAIL_KEY]
        val name = preferences[USER_NAME_KEY]
        val id = preferences[USER_ID_KEY]
        Log.d("UserPreferences", "Dados recuperados: userName=$name, userEmail=$email")
        return Pair(name, email)
    }

    /**
     * Salva uma lista de categorias no DataStore.
     */
    suspend fun saveCategories(categories: List<Category>) {
        context.dataStore.edit { preferences ->
            preferences[CATEGORIES_KEY] = categories.toJson()
        }
    }

    /**
     * Salva uma lista de estabelecimentos no DataStore.
     */
    suspend fun saveEstablishments(establishments: List<Establishment>) {
        context.dataStore.edit { preferences ->
            preferences[ESTABLISHMENTS_KEY] = establishments.toJson()
        }
    }

    /**
     * Limpa todas as informações do usuário e dados persistentes.
     */
    suspend fun clearUserInfo() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    // Métodos de conversão JSON usando Gson

    /**
     * Converte uma string JSON para uma lista de objetos de tipo genérico.
     */
    private inline fun <reified T> String.fromJsonToList(): List<T> {
        val listType = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(this, listType)
    }

    /**
     * Converte uma lista de objetos para uma string JSON.
     */
    private fun <T> List<T>.toJson(): String {
        return gson.toJson(this)
    }
}
