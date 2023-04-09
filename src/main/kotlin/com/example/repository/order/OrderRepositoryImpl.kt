package com.example.repository.order

import com.example.database.MongoDatabase
import com.example.model.cart.Cart
import com.example.model.cart.CartDto
import com.example.model.order.Order
import com.example.util.productToDocument
import com.example.util.toProduct
import org.bson.Document

class OrderRepositoryImpl : OrderRepository {
    private val orderCollection = MongoDatabase.database.getCollection("orders")

    override fun order(order: Order): Boolean {
        return orderCollection.insertOne(orderToDoc(order)).wasAcknowledged()
    }

    fun orderToDoc(order: Order) = Document().append("userId",order.userId)
        .append("amount",order.amount).append("paymentId",order.paymentId).append("currency",order.currency).append("status",order.status)
        .append("products",order.products.map { it.toProduct().productToDocument() }).append("createdAt",order.createdAt)


}