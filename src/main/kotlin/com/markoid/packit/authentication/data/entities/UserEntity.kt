package com.markoid.packit.authentication.data.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class UserEntity(
    @Id
    override val id: ObjectId = ObjectId.get(),
    var assignedTrip: String = "",
    override val email: String,
    override val firebaseToken: String = "",
    override val lastName: String,
    override val name: String,
    override val password: String,
) : IUserEntity