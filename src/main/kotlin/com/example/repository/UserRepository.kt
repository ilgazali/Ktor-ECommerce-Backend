package com.example.repository

import com.example.model.User
import org.litote.kmongo.Id
import java.util.*

interface UserRepository {
    fun getUsers(): ArrayList<User>
    fun getUserByEmail(email:String): User?
    fun getUserById(id: String): User?
    fun createUser(user: User): Id<User>?
}