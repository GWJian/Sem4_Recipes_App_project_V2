package com.gwj.recipesappV2.data.model

import java.util.UUID

// Data class for User with properties id, name, email, and profilePicUrl
data class User(
    val id: String? = null,
    val name: String,
    val email: String,
    val profilePicUrl: String = "",
) {
    // convert User object to a HashMap
    fun toHashMap(): HashMap<String, String?> {
        return hashMapOf(
            "id" to id,
            "name" to name,
            "email" to email,
            "profilePicUrl" to profilePicUrl
        )
    }

    // Companion object for User class
    companion object {
        // convert a HashMap to a User object
        fun fromHashMap(hash: Map<String, Any>): User {
            return User(
                id = hash["id"].toString(),
                name = hash["name"].toString(),
                email = hash["email"].toString(),
                profilePicUrl = hash["profilePicUrl"].toString()
            )
        }
    }
}
