package com.example.service

import com.example.model.User
import com.example.repository.UserRepository
import org.litote.kmongo.Id

class AuthenticationService(private val userRepository: UserRepository) {
    fun login(email: String, password: String): User? {
        val user = userRepository.getUserByEmail(email)
        return if (user != null && user.password == password) user else null
    }

    fun register(user: User): Id<User>? {
        return userRepository.createUser(user)
    }
}
