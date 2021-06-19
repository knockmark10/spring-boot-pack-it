package com.markoid.packit.authentication.data.entities

import org.springframework.data.mongodb.core.mapping.Document

@Document("drivers")
data class DriverEntity(
    val email: String,
    val firebaseToken: String = "",
    val lastName: String,
    val name: String,
    val password: String
)