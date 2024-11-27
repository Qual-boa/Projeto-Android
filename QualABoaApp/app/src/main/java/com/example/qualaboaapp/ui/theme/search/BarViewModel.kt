package com.example.qualaboaapp.ui.theme.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BarViewModel(val repository : BarRepository) : ViewModel() {

    private val _bars = MutableStateFlow<List<BarResponse>>(emptyList())
    val bars: StateFlow<List<BarResponse>> = _bars

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun searchBars(name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val barList = repository.searchBars(name)
                _bars.value = barList
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
