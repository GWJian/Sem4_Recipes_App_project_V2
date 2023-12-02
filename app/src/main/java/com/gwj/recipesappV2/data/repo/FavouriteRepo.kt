package com.gwj.recipesappV2.data.repo

import com.gwj.recipesappV2.data.model.favouriteRecipe
import kotlinx.coroutines.flow.Flow

interface favouriteRepo {

    // get all favourite recipes of a user. It returns a Flow of list of favouriteRecipe.
    fun getAllfavouriteRecipe(userId: String): Flow<List<favouriteRecipe>>

    // add a recipe to the favourites of a user. It returns a Flow of Boolean indicating the success of the operation.
    suspend fun AddTofavourite(userId: String, recipeId: favouriteRecipe): Flow<Boolean>

    // remove a recipe from the favourites of a user.
    suspend fun RemoveFromfavourite(userId: String, recipeId: String)

    // check if a recipe is in the favourites of a user. It returns a Boolean indicating whether the recipe is a favourite or not.
    suspend fun isfavourite(userId: String, recipeId: String): Boolean

    // get the number of favourites of a recipe. It returns a Flow of Int.
    fun getfavouriteCount(recipeId: String): Flow<Int>
}