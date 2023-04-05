package com.example.util

import com.example.model.cart.Cart
import com.example.model.cart.CartDto
import com.example.model.product.Product
import com.example.model.user.User
import com.example.model.product.ProductDto
import com.example.model.user.UserDto
import org.bson.Document

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
        onSale = this.onSale,
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
    onSale = this.onSale,
    rating = this.rating
)

fun CartDto.toCart(): Cart = Cart(userId = this.userId, products = this.products)

fun Document.documentToProductDto(): ProductDto {
    return ProductDto(
        _id = this.get("_id").toString(), // to get id of object from db use this get()
        title= this.getString("title"),
        price= this.getDouble("price"),
        description= this.getString("description"),
        count= this.getInteger("count"),//sepete kac adet urun eklendi
        stockCount = this.getInteger("stockCount"),
        category=this.getString("category"),
        image=this.getString("image"),
        image_two=this.getString("image_two"),
        image_three=this.getString("image_three"),
        sale_state=this.getInteger("sale_state"), //kac adet
        salePrice=this.getDouble("salePrice"),
        onSale = this.getBoolean("onSale"),
        rating= this.getDouble("rating")
    )
}
fun Product.productToDocument(): Document {
    return Document() // BURADA ID ILE ILGILI HATA ALABILIRIM AFTER CHECKKKK
        .append("title", this.title)
        .append("price", this.price)
        .append("description", this.description)
        .append("count", this.count)
        .append("stockCount",this.stockCount)
        .append("category", this.category)
        .append("image", this.image)
        .append("image_two", this.image_two)
        .append("image_three", this.image_three)
        .append("sale_state", this.sale_state)
        .append("salePrice", this.salePrice)
        .append("onSale",this.onSale)
        .append("rating", this.rating)
}


