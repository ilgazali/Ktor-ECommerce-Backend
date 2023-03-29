package com.example.util

import com.example.model.User
import com.example.model.dto.UserDto

fun User.toDto(): UserDto =
    UserDto(
        id = this.id.toString(),
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        password = this.password

    )

fun UserDto.toUser(): User =
    User(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        password = this.password
    )