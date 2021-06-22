package com.markoid.packit.authentication.data.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("drivers")
data class DriverEntity(
    @Id
    override val id: ObjectId = ObjectId.get(),
    override val email: String,
    override val firebaseToken: String = "",
    override val lastName: String,
    override val name: String,
    override val password: String
) : IUserEntity