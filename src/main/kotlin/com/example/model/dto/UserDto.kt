package com.example.model.dto

import com.example.model.User
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id

@Serializable
data class UserDto(
                   var id: String? = null,
                   val firstName: String,
                   val lastName: String,
                   val password: String,
                   val email: String
                   )
