package com.example.repository.product

import com.example.database.MongoDatabase
import com.example.model.product.Product
import com.example.model.product.ProductDto
import com.example.util.documentToProductDto
import com.example.util.productToDocument
import org.bson.Document
import org.litote.kmongo.eq
import org.litote.kmongo.or
import org.litote.kmongo.regex

class ProductRepositoryImpl : ProductRepository{

    private val productCollection = MongoDatabase.database.getCollection("products")

    override fun addProduct(product: Product): Boolean {
        val document = product.productToDocument()
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
            it.documentToProductDto()
        }.forEach {
            list.add(it)
        }
        return list
    }

    override fun getOnSaleProducts(): ArrayList<ProductDto> {
        val list = ArrayList<ProductDto>()
            productCollection.find(ProductDto::onSale eq true).map {
            it.documentToProductDto()
        }.forEach{
            list.add(it)
        }
      return list
    }

    override fun getProductsByCategory(category: String): ArrayList<ProductDto>{
        val list = ArrayList<ProductDto>()
        productCollection.find(ProductDto::category eq category).map{
            it.documentToProductDto()
        }.forEach {
            list.add(it)
        }
        return list
    }

    override fun searchProducts(query: String): List<ProductDto> {
        val regexQuery = Regex.escape(query).toRegex(RegexOption.IGNORE_CASE)
        val searchFilter = or(
            ProductDto::title regex regexQuery,
            ProductDto::description regex regexQuery,
            ProductDto::category regex regexQuery
        )
       val result = productCollection.find(searchFilter).map {
            it.documentToProductDto()
        }

        return result.toList()
    }


}