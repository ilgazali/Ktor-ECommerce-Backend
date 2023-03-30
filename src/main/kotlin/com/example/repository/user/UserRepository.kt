package com.example.repository.user

import com.example.model.User
import com.example.model.dto.UserDto
import org.litote.kmongo.Id
import java.util.*

interface UserRepository {
    fun getUsers(): ArrayList<UserDto>
    fun getUserByEmail(email:String): UserDto?
    fun getUserById(id: String): UserDto?
    fun createUser(user: User): Boolean
}