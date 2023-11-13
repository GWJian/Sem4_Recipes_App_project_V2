package com.gwj.recipesappV2.data.repo

import com.gwj.recipesappV2.data.model.Meal

interface GetAllMealsRepo {
    suspend fun getAllMeals(): List<Meal>
    suspend fun searchAllMeals(query: String): List<Meal>
    suspend fun getCategoryMeals(category: String): List<Meal>
    suspend fun getMealByName(name: String): Meal?
}