package com.gwj.recipesappV2.data.repo

import com.gwj.recipesappV2.data.model.User


interface UserRepo {

    // add a user to the database
    suspend fun addUser(id: String, user: User)

    // get a user by its id. It returns a User if found, null otherwise.
    suspend fun getUser(id: String): User?

    // update a user. It returns a Unit.
    suspend fun updateUser(id: String, user: User)
}