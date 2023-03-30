package com.example.model.dto

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.litote.kmongo.Id

data class UserDto(

    var _id: String? = null,
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String
                   )
