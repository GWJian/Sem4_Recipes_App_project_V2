package com.gwj.recipesappV2.data.repo

import com.gwj.recipesappV2.data.model.FavoriteRecipe
import kotlinx.coroutines.flow.Flow

interface FavoriteRepo {

    fun getALlFavoriteRecipe():Flow<List<FavoriteRecipe>>

    suspend fun insert(favoriteRecipe: FavoriteRecipe)

    suspend fun getFavoriteRecipe(id: String): FavoriteRecipe?

    suspend fun update(id: String, favoriteRecipe: FavoriteRecipe)

    suspend fun delete(id: String)

}