package com.gwj.recipesappV2.data.api

import com.gwj.recipesappV2.data.model.CategoryResp
import com.gwj.recipesappV2.data.model.MealNameBySearchResp
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    //Get all food categories,beef ,chicken, dessert, etc
    //www.themealdb.com/api/json/v1/1/categories.php
    @GET("/api/json/v1/1/categories.php")
    suspend fun getAllFoodCategories(): CategoryResp

    //Get all meals by category
    //www.themealdb.com/api/json/v1/1/filter.php?c=
    @GET("/api/json/v1/1/filter.php")
    suspend fun getAllMealsByCategory(@Query("c") query: String): MealNameBySearchResp

    //Get meal by name but since its empty will show all 25 meals
    //https://www.themealdb.com/api/json/v1/1/search.php?s=
    @GET("/api/json/v1/1/search.php")
    suspend fun getAllMeals(@Query("s") query: String): MealNameBySearchResp


}