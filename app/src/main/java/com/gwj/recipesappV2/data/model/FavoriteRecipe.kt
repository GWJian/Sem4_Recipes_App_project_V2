package com.gwj.recipesappV2.data.model


data class FavoriteRecipe(
    val id: String = "",
    val idMeal: String = "",
    val strMeal: String = "",
    val strMealThumb: String = "",
)
{
    fun toHashMap(): HashMap<String, Any> {

        return hashMapOf<String, Any>(
            "idMeal" to idMeal,
            "strMeal" to strMeal,
            "strMealThumb" to strMealThumb,
        )
    }

    companion object {
        fun fromHashMap(hash: Map<String, Any>): FavoriteRecipe {
            return FavoriteRecipe(
                id = hash["id"] as String,
                idMeal = hash["idMeal"] as String,
                strMeal = hash["strMeal"] as String,
                strMealThumb = hash["strMealThumb"] as String
            )
        }
    }

}
