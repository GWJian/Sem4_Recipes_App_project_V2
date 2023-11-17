package com.gwj.recipesappV2.data.repo

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.getValue
import com.gwj.recipesappV2.data.model.FavoriteRecipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FavoriteRepoRealTimeImpl(
    private val dbRef: DatabaseReference
) : FavoriteRepo {
    override fun getALlFavoriteRecipe(): Flow<List<FavoriteRecipe>> {
        TODO("Not yet implemented")
    }

    override suspend fun AddToFavorite(userId: String, recipe: FavoriteRecipe): Flow<Boolean> {
        return flow {
            dbRef.child(userId).child(recipe.id).setValue(recipe).await()
            emit(true)
        }.catch {
            emit(false)
        }
    }

    override suspend fun getFavorite(userId: String, id: String): FavoriteRecipe {
        TODO("Not yet implemented")
    }

    override suspend fun RemoveFromFavorite(userId: String, id: String) {
        dbRef.child(userId).child(id).removeValue().await()
    }



}