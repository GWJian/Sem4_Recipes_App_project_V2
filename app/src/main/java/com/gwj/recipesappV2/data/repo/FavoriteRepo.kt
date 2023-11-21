package com.gwj.recipesappV2.data.repo

import com.gwj.recipesappV2.data.model.FavoriteRecipe
import kotlinx.coroutines.flow.Flow

interface FavoriteRepo {

    fun getAllFavoriteRecipe(userId: String): Flow<List<FavoriteRecipe>>

    //we use Boolean because we want to know it is success to set into database or not
    //we set userId because we want to know which user is adding the recipe,so only the user can see it in their favorite list
    suspend fun AddToFavorite(userId: String, recipe: FavoriteRecipe): Flow<Boolean>

    suspend fun getFavorite(userId: String, id: String): FavoriteRecipe?

    suspend fun RemoveFromFavorite(userId: String, id: String)

    suspend fun isFavorite(userId: String, id: String): Boolean

}