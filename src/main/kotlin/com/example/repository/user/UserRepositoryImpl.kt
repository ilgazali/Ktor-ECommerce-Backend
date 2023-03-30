package com.example.repository.user

import com.example.database.MongoDatabase
import com.example.model.User
import com.example.model.dto.UserDto
import com.mongodb.client.model.Filters.eq
import org.bson.Document
import java.util.*

class UserRepositoryImpl : UserRepository {

    private val usersCollection = MongoDatabase.database.getCollection("users")


    override fun getUsers(): ArrayList<UserDto> {
        val list = ArrayList<UserDto>()

           usersCollection.find().map{
               documentToUserDto(it)
            }.forEach {
                list.add(it)
            }
            return list
        }


    override fun getUserByEmail(email: String): UserDto? {
        val document = usersCollection.find(eq("email", email)).first()
        return if (document != null) documentToUserDto(document) else null
    }


    override fun getUserById(id: String): UserDto? {
        val document = usersCollection.find(eq("id", id)).first()
        return if (document != null) documentToUserDto(document) else null
    }


    override fun createUser(user: User): Boolean {
        val document = userToDocument(user)
        var check = usersCollection.insertOne(document).wasAcknowledged()
        if (!check){
            return false
        }
        return true
    }

    private fun documentToUserDto(document: Document): UserDto {
        return UserDto(
            _id = document.get("_id").toString(), // to get id of object from db use this get()
            firstName = document.getString("firstName"),
            lastName = document.getString("lastName"),
            password = document.getString("password"),
            email = document.getString("email")
        )
    }

    private fun userToDocument(user: User): Document {
        return Document()
            //.append("_id",user._id.toString())
            .append("firstName", user.firstName)
            .append("lastName",user.lastName)
            .append("password", user.password)
            .append("email", user.email)
    }
}
