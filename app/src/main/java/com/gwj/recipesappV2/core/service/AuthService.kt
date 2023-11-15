package com.gwj.recipesappV2.core.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.tasks.await

class AuthService(
    private val auth: FirebaseAuth = Firebase.auth
) {
    suspend fun register(email: String, pass: String): FirebaseUser? {
        val task = auth.createUserWithEmailAndPassword(email, pass).await()
        return task.user
    }

    suspend fun login(email: String, password: String): FirebaseUser? {
        val task = auth.signInWithEmailAndPassword(email, password).await()
        return task.user
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun logout() {
        auth.signOut()
    }
}