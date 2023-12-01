package com.gwj.recipesappV2.data.repo

import com.gwj.recipesappV2.data.model.Meal

interface GetAllMealsRepo {

    // get all meals. It returns a List of Meal.
    suspend fun getAllMeals(): List<Meal>

    // search all meals by a query string. It returns a List of Meal that match the query.
    suspend fun searchAllMeals(query: String): List<Meal>

    // get all meals of a specific category. It returns a List of Meal that belong to the category.
    suspend fun getCategoryMeals(category: String): List<Meal>

    // get a meal by its name. It returns a Meal if found, null otherwise.
    suspend fun getMealByName(name: String): Meal?
}