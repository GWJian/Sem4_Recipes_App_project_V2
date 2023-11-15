package com.gwj.recipesappV2.data.repo

import com.gwj.recipesappV2.data.model.User


interface UserRepo {
    suspend fun addUser(id: String, user: User)
    suspend fun getUser(id: String): User?
    suspend fun updateUser(id: String, user: User)
}