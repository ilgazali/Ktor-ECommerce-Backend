package com.example.util

import com.example.model.product.Product
import com.example.model.user.User
import com.example.model.product.ProductDto
import com.example.model.user.UserDto

fun User.toDto(): UserDto =
    UserDto(
        _id = this._id.toString(),
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        password = this.password,
        username = this.username,
        salt = this.salt

    )

fun UserDto.toUser(): User =
    User(
        salt= this.salt,
        username = this.username,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        password = this.password
    )

fun Product.toProductDto(): ProductDto =
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

fun ProductDto.toProduct(): Product = Product(title= this.title,
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

