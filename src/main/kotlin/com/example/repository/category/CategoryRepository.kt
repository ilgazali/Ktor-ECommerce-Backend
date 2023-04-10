package com.example.repository.category

import com.example.model.requests.CategoryRequest

interface CategoryRepository {
    fun addCategory(category: String): Boolean
    fun getCategories(): List<String>
}