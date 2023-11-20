package com.gwj.recipesappV2.data.model

import java.util.UUID

data class User(
    //val id: String? = UUID.randomUUID().toString(), //generate random id to each user
    val id: String? = null,
    val name: String,
    val email: String,
    val profilePicUrl: String = "",
) {
    fun toHashMap(): HashMap<String, String?> {
        return hashMapOf(
            "id" to id,
            "name" to name,
            "email" to email,
            "profilePicUrl" to profilePicUrl
        )
    }

    companion object {
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
