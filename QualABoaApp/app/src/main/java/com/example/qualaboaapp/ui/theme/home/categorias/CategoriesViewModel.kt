package com.example.qualaboaapp.ui.theme.home.categorias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class CategoriesViewModel(private val repository: CategoriaRepository) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    private val _popularCategories = MutableStateFlow<List<Category>>(emptyList())
    val popularCategories: StateFlow<List<Category>> get() = _popularCategories

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val categories = repository.getCategorias()
                _categories.emit(categories)
                selectRandomPopularCategories(categories)
            } catch (e: Exception) {
                e.printStackTrace()
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
}
