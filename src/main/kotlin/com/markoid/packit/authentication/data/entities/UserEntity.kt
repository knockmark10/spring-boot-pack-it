package com.markoid.packit.authentication.data.entities

import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class UserEntity(
    val assignedTrip: String = "",
    val email: String,
    val firebaseToken: String = "",
    val lastName: String,
    val name: String,
    val password: String,
)