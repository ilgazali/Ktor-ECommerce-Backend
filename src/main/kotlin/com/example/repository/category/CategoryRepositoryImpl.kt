package com.example.repository.category

import com.example.database.MongoDatabase
import com.example.model.requests.CategoryRequest
import com.example.model.user.User
import com.example.model.user.UserDto
import org.bson.Document

class CategoryRepositoryImpl : CategoryRepository {

    private val categoryCollection = MongoDatabase.database.getCollection("categories")

    override fun addCategory(category: String): Boolean {
        val result = categoryCollection.insertOne(Document().append("category",category))
        return result.wasAcknowledged()
    }

    override fun getCategories(): List<String> {
        val categoryList = ArrayList<String>()
        categoryCollection.find().map {
            it.get("category").toString()
        }.forEach {
            categoryList.add(it)
        }
        return categoryList
    }



}