package com.gwj.recipesappV2.data.repo

import com.gwj.recipesappV2.data.model.Category

interface CategoryRepo {
    suspend fun getAllCategories(): List<Category>
}