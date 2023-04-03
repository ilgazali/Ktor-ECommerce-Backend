package com.example.model.user

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class User(
    @BsonId
    var _id: Id<User>? = null,
    val username: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String,
    val salt:String? =null
)
