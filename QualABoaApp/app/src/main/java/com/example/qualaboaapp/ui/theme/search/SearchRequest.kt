package com.example.qualaboaapp.ui.theme.search

data class SearchRequest(
    val name: String,
    val categories: List<Category>
)

data class Category(
    val categoryType: Int,
    val category: Int
)
