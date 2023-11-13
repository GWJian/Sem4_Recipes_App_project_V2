package com.gwj.recipesappV2.data.model

//https://www.themealdb.com/api/json/v1/1/categories.php
data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
)