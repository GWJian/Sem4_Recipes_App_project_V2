package com.gwj.recipesappV2.data.repo

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.gwj.recipesappV2.data.model.FavoriteRecipe
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FavoriteRepoRealTimeImpl(
    private val dbRef: DatabaseReference,
) : FavoriteRepo {
    override fun getAllFavoriteRecipe(userId: String) = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val recipe = mutableListOf<FavoriteRecipe>()
                for (recipeSnapshot in snapshot.children) {
                    //Log.d("debugging_FavoriteRepoRealTimeImpl", "getAllFavoriteRecipe" + recipeSnapshot.key.toString())
                    recipeSnapshot.getValue<FavoriteRecipe>()?.let {
                        recipe.add(it.copy(id = recipeSnapshot.key ?: ""))
                    }
                }
                trySend(recipe)
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }
        }

        dbRef.child(userId).addValueEventListener(listener)
        awaitClose()
    }

    override suspend fun AddToFavorite(userId: String, recipe: FavoriteRecipe): Flow<Boolean> {
        //make a boolean flow, if success return true save to database, if fail return false
        return flow {
            //save the recipe to Realtime database with userId -> recipe.id and the recipe name
            dbRef.child(userId).child(recipe.id).setValue(recipe).await()
            //if success return true
            emit(true)
        }.catch {
            //if any error happen return false
            emit(false)
        }
    }

    override suspend fun RemoveFromFavorite(userId: String, id: String) {
        //删除数据库中的数据,通过userId和recipe.id来删除
        dbRef.child(userId).child(id).removeValue().await()
    }

    override suspend fun isFavorite(userId: String, id: String): Boolean {
        // Get a reference to the specific recipe in the database
        val recipeRef = dbRef.child(userId).child(id)

        // Try to get the value at the reference
        val snapshot = recipeRef.get().await()

        // If the snapshot exists, the recipe is marked as favorite
        return snapshot.exists()
    }
}

// This is child
//dbRef (root)
//|
//|--- userId (child of root)
//|    |
//|    |--- recipe.id (child of userId)
//|    |    |
//|    |    |--- recipe (value at this node)
//|
//|--- anotherUserId (another child of root)
//|
//|--- anotherRecipe.id (child of anotherUserId)
//|
//|--- anotherRecipe (value at this node)