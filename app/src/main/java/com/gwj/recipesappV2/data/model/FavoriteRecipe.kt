package com.gwj.recipesappV2.data.model


data class FavoriteRecipe(
    val idMeal: String,
    val strMeal: String,
    val id:String = "",
    val strMealThumb: String = "",
)
//{
//    fun toHashMap(): HashMap<String, Any> {
//
//        return hashMapOf<String, Any>(
//            "id" to idMeal,
//            "name" to strMeal,
//        )
//    }
//
//    companion object {
//        fun fromHashMap(hash: Map<String, Any>): FavoriteRecipe {
//            return FavoriteRecipe(
//                idMeal = hash["id"].toString(),
//                strMeal = hash["name"].toString(),
//                id = hash["id"].toString(),
//            )
//        }
//    }
//
//}
