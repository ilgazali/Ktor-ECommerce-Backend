package com.example.repository.product

import com.example.database.MongoDatabase
import com.example.model.Product
import com.example.model.Rating
import com.example.model.User
import com.example.model.dto.ProductDto
import com.example.model.dto.UserDto
import org.bson.Document
import org.bson.conversions.Bson

class ProductRepositoryImpl : ProductRepository{
    private val productCollection = MongoDatabase.database.getCollection("products")

    override fun addProduct(product: Product): Boolean {
        val document = productToDocument(product)
        val check = productCollection.insertOne(document).wasAcknowledged()
        if (!check){
            return false
        }
        return true
    }

    override fun deleteAll(): Boolean {
      val result = productCollection.deleteMany(Document()).wasAcknowledged()
        if(!result){
            return false
        }
        return true
    }

    override fun getProducts(): ArrayList<ProductDto> {
        val list = ArrayList<ProductDto>()
        productCollection.find().map{
            documentToProductDto(it)
        }.forEach {
            list.add(it)
        }
        return list
    }

    override fun getProductByUser(): ArrayList<ProductDto> {
        TODO("Not yet implemented")
    }

    private fun documentToProductDto(document: Document): ProductDto {
        return ProductDto(
            _id = document.get("_id").toString(), // to get id of object from db use this get()
            title= document.getString("title"),
         price= document.getDouble("price"),
         description= document.getString("description"),
         count= document.getInteger("count"),//sepete kac adet urun eklendi
             stockCount = document.getInteger("stockCount"),
         category=document.getString("category"),
         image=document.getString("image"),
         image_two=document.getString("image_two"),
         image_three=document.getString("image_three"),
         sale_state=document.getInteger("sale_state"), //kac adet
         salePrice=document.getDouble("salePrice"),
         rating= document.getDouble("raiting")
        )
    }

    private fun productToDocument(product: Product): Document {
        return Document()
            .append("title", product.title)
            .append("price", product.price)
            .append("description", product.description)
            .append("count", product.count)
            .append("stockCount",product.stockCount)
            .append("category", product.category)
            .append("image", product.image)
            .append("image_two", product.image_two)
            .append("image_three", product.image_three)
            .append("sale_state", product.sale_state)
            .append("salePrice", product.salePrice)
            .append("raiting", product.rating)
    }

}