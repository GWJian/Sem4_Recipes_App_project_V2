package com.gwj.recipesappV2.data.repo

import android.util.Log
import com.gwj.recipesappV2.data.api.RecipeApi
import com.gwj.recipesappV2.data.model.Meal

class GetAllMealsRepoImpl(private val RecipeApi: RecipeApi) : GetAllMealsRepo {

    // get all meals by calling the API. If the API returns null, return an empty list.
    override suspend fun getAllMeals(): List<Meal> {
        return RecipeApi.getAllMeals("").meals ?: emptyList()
    }

    // search all meals by a query string by calling the API. If the API returns null, return an empty list.
    override suspend fun searchAllMeals(query: String): List<Meal> {
        return RecipeApi.getAllMeals(query).meals ?: emptyList()
    }

    // get all meals of a specific category by calling the API. If the API returns null, return an empty list.
    override suspend fun getCategoryMeals(category: String): List<Meal> {
        return RecipeApi.getAllMealsByCategory(category).meals ?: emptyList()
    }

    //这给是在搜索框里面输入名字，然后返回一个我要的meal/ when we search for a meal by name,return the meal we want
    override suspend fun getMealByName(name: String): Meal? {
        return RecipeApi.getAllMeals(name).meals?.let {
            it.firstOrNull()
        }
    }
}