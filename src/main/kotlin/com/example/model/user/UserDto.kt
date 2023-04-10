package com.example.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    var _id: String? = null,
    val username: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String,
    var salt:String? = null
                   )
