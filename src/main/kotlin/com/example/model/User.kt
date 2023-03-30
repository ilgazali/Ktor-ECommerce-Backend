package com.example.model

import kotlinx.serialization.Contextual
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class User(
    @Contextual
    var _id: Id<User>? = null,
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String
)
