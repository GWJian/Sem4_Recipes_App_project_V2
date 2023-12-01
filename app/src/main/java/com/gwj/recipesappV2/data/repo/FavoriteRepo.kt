package com.gwj.recipesappV2.data.repo

import com.gwj.recipesappV2.data.model.FavoriteRecipe
import kotlinx.coroutines.flow.Flow

interface FavoriteRepo {

    // get all favorite recipes of a user. It returns a Flow of list of FavoriteRecipe.
    fun getAllFavoriteRecipe(userId: String): Flow<List<FavoriteRecipe>>

    // add a recipe to the favorites of a user. It returns a Flow of Boolean indicating the success of the operation.
    suspend fun AddToFavorite(userId: String, recipe: FavoriteRecipe): Flow<Boolean>

    // remove a recipe from the favorites of a user.
    suspend fun RemoveFromFavorite(userId: String, id: String)

    // check if a recipe is in the favorites of a user. It returns a Boolean indicating whether the recipe is a favorite or not.
    suspend fun isFavorite(userId: String, id: String): Boolean

}