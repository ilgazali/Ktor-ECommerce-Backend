package com.example.service

import com.example.model.User
import com.example.model.dto.UserDto
import com.example.repository.user.UserRepository

class AuthenticationService(private val userRepository: UserRepository) {
    fun login(email: String, password: String): UserDto? {
        val user = userRepository.getUserByEmail(email)
        return if (user != null && user.password == password) user else null
    }

    fun register(user: User): Boolean {
        return userRepository.createUser(user)
    }
}
