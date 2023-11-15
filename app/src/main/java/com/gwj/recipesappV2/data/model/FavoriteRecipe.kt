package com.gwj.recipesappV2.data.model

data class FavoriteRecipe(
    val idMeal: String,
    val strMeal: Meal,
) {
    fun markAsFavorite(idMeal: String, strMeal: Meal) {
        val favoriteRepo = FavoriteRecipe(idMeal, strMeal)

        //TODO MORE?
    }
}
