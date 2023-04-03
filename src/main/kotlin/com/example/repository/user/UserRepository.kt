package com.example.repository.user

import com.example.model.user.User
import com.example.model.user.UserDto

interface UserRepository {
   // fun getUsers(): ArrayList<UserDto>
    // fun getUserByEmail(email:String): UserDto?
   // fun getUserById(id: String): UserDto?
    //  fun createUser(user: User): Boolean

     fun getUserByUsername(username: String): UserDto?
     fun insertUser(user: User): Boolean
}