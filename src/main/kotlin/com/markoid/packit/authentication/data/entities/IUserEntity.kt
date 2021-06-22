package com.markoid.packit.authentication.data.entities

import org.bson.types.ObjectId

interface IUserEntity {
    val id: ObjectId
    val email: String
    val firebaseToken: String
    val lastName: String
    val name: String
    val password: String
}