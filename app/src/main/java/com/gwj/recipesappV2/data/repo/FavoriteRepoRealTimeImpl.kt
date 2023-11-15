package com.gwj.recipesappV2.data.repo

import com.google.firebase.database.DatabaseReference
import com.gwj.recipesappV2.data.model.FavoriteRecipe
import kotlinx.coroutines.flow.Flow

class FavoriteRepoRealTimeImpl(
    private val dbRef:DatabaseReference
) : FavoriteRepo {
    override fun getALlFavoriteRecipe(): Flow<List<FavoriteRecipe>> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(favoriteRecipe: FavoriteRecipe) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteRecipe(id: String): FavoriteRecipe? {
        TODO("Not yet implemented")
    }

    override suspend fun update(id: String, favoriteRecipe: FavoriteRecipe) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String) {
        TODO("Not yet implemented")
    }

}