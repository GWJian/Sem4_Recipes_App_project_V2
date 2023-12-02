package com.gwj.recipesappV2.data.repo

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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
            //this method will be called when the data is changed in the database
            override fun onDataChange(snapshot: DataSnapshot) {

                // create a list of recipe to store the data from database
                val recipe = mutableListOf<FavoriteRecipe>()
                //loop through the snapshot to get the data
                for (recipeSnapshot in snapshot.children) {
                    //if the value is not empty, add the value to the list
                    recipeSnapshot.getValue<FavoriteRecipe>()?.let {
                        recipe.add(it.copy(id = recipeSnapshot.key ?: ""))
                    }
                    //Log.d("debugging_FavoriteRepoRealTimeImpl", "getAllFavoriteRecipe" + recipeSnapshot.key.toString())
                }
                trySend(recipe)
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }
        }

        //add the listener to the database reference, so when the data is changed, the listener will be called
        dbRef.child(userId).addValueEventListener(listener)
        awaitClose()
    }

    override suspend fun AddToFavorite(userId: String, recipeId: FavoriteRecipe): Flow<Boolean> {
        //make a boolean flow, if success return true save to database, if fail return false
        return flow {
            //save the recipe to Realtime database with userId -> recipe.id and the recipe name
            dbRef.child(userId).child(recipeId.id).setValue(recipeId).await()
            //update the favoriteCount in the database
            val favoriteCountRef = dbRef.child("favoriteCount").child(recipeId.idMeal).child("count")
            val currentCount = favoriteCountRef.get().await().getValue(Int::class.java) ?: 0
            favoriteCountRef.setValue(currentCount + 1).await()
            //if success return true
            emit(true)
        }.catch {
            //if any error happen return false
            emit(false)
        }
    }

    override suspend fun RemoveFromFavorite(userId: String, recipeId: String) {
        //删除数据库中的数据,通过userId和recipe.id来删除/ delete the data in the database by userId and recipe.id
        dbRef.child(userId).child(recipeId).removeValue().await()
        //update the favoriteCount in the database
        val favoriteCountRef = dbRef.child("favoriteCount").child(recipeId).child("count")
        val currentCount = favoriteCountRef.get().await().getValue(Int::class.java) ?: 0
        favoriteCountRef.setValue(currentCount - 1).await()
    }

    override suspend fun isFavorite(userId: String, recipeId: String): Boolean {
        // Get a reference to the specific recipe in the database
        val recipeRef = dbRef.child(userId).child(recipeId)

        // Try to get the value at the reference
        val snapshot = recipeRef.get().await()

        // If the snapshot exists, the recipe is marked as favorite
        return snapshot.exists()
    }

    override fun getFavoriteCount(recipeId: String): Flow<Int> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val count = snapshot.getValue<Int>() ?: 0
                trySend(count)
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }
        }

        dbRef.child("favoriteCount").child(recipeId).child("count").addValueEventListener(listener)
        awaitClose()
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