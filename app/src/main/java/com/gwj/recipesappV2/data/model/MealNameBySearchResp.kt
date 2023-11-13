package com.gwj.recipesappV2.data.model

//https://www.themealdb.com/api/json/v1/1/search.php?s=
//we use this to get all meals since its empty it will show all meals
//we also use this to get meal by name by adding the name to the end of the url
data class MealNameBySearchResp(
    val meals: List<Meal>?
)




