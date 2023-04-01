package com.example.util

import com.example.model.Product
import com.example.model.User
import com.example.model.dto.ProductDto
import com.example.model.dto.UserDto
import org.litote.kmongo.json
import org.litote.kmongo.variable

fun User.toDto(): UserDto =
    UserDto(
        _id = this._id.toString(),
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

fun Product.toProductDto():ProductDto =
    ProductDto(
    _id = this._id.toString(),
        title= this.title,
    price = this.price,
    description = this.description,
    count = this.count,
        stockCount = this.stockCount,

                category = this.category,
    image = this.image,
    image_two = this.image_two,
    image_three = this.image_three,
    sale_state = this.sale_state,
    salePrice = this.salePrice,
    rating = this.rating
)

fun ProductDto.toProduct():Product = Product(title= this.title,
    price = this.price,
    description = this.description,
    count = this.count,
    stockCount = this.stockCount,
    category = this.category,
    image = this.image,
    image_two = this.image_two,
    image_three = this.image_three,
    sale_state = this.sale_state,
    salePrice = this.salePrice,
    rating = this.rating
)

