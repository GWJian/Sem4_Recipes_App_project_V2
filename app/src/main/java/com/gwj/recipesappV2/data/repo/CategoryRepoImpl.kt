package com.gwj.recipesappV2.data.repo

import com.gwj.recipesappV2.data.api.RecipeApi
import com.gwj.recipesappV2.data.model.Category

class CategoryRepoImpl(private val RecipeApi: RecipeApi) : CategoryRepo {
    override suspend fun getAllCategories(): List<Category> {
        return RecipeApi.getAllFoodCategories().categories
    }
}