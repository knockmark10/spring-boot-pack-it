package com.markoid.packit.authentication.data.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("drivers")
data class DriverEntity(
    @Id val id: ObjectId = ObjectId.get(),
    val email: String,
    val firebaseToken: String = "",
    val lastName: String,
    val name: String,
    val password: String
)