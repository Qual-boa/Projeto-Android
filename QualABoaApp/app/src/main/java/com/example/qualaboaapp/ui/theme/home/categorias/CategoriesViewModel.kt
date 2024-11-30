package com.example.qualaboaapp.ui.theme.home.categorias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qualaboaapp.ui.theme.utils.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.random.Random

class CategoriesViewModel(
    private val repository: CategoriaRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    private val _popularCategories = MutableStateFlow<List<Category>>(emptyList())
    val popularCategories: StateFlow<List<Category>> get() = _popularCategories

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _loading.emit(true)
            _errorMessage.emit(null)

            try {
                val savedCategories = userPreferences.categories.firstOrNull()
                if (!savedCategories.isNullOrEmpty()) {
                    _categories.emit(savedCategories)
                    selectRandomPopularCategories(savedCategories)
                } else {
                    val fetchedCategories = repository.getCategorias()
                    _categories.emit(fetchedCategories)
                    userPreferences.saveCategories(fetchedCategories)
                    selectRandomPopularCategories(fetchedCategories)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.emit("Erro ao carregar categorias: ${e.message}")
            } finally {
                _loading.emit(false)
            }
        }
    }


    private fun selectRandomPopularCategories(categories: List<Category>) {
        if (categories.size > 5) {
            _popularCategories.value = categories.shuffled().take(5)
        } else {
            _popularCategories.value = categories
        }
    }

    fun refreshCategories() {
        viewModelScope.launch {
            _loading.emit(true)
            _errorMessage.emit(null)

            try {
                val fetchedCategories = repository.getCategorias()
                _categories.emit(fetchedCategories)
                userPreferences.saveCategories(fetchedCategories) // Atualizar persistência
                selectRandomPopularCategories(fetchedCategories)
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.emit("Erro ao atualizar categorias: ${e.message}")
            } finally {
                _loading.emit(false)
            }
        }
    }
}
