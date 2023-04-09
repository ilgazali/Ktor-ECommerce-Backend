package com.example.repository.order

import com.example.model.cart.CartDto
import com.example.model.order.Order

interface OrderRepository {
    fun order(order: Order):Boolean
}