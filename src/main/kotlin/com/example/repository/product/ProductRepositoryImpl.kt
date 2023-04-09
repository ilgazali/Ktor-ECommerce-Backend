package com.example.repository.product

import com.example.database.MongoDatabase
import com.example.model.product.Product
import com.example.model.product.ProductDto
import com.example.util.documentToProductDto
import com.example.util.productToDocument
import org.bson.Document
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.id.toId

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

    override fun getProductById(id: String): Pair<Boolean,ProductDto?> {


        val doc =  productCollection.findOne(ProductDto::_id eq ObjectId(id))

        println()
        println( "ProductDto._id: ${doc?.get("_id").toString()} ============================== ${id}")

        if (doc != null) {

            return Pair(true, doc.documentToProductDto())
        }
        return Pair(false,null)
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