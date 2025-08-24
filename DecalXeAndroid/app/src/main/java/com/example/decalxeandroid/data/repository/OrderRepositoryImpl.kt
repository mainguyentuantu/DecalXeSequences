package com.example.decalxeandroid.data.repository

import com.example.decalxeandroid.data.api.OrderApi
import com.example.decalxeandroid.data.dto.OrderDto
import com.example.decalxeandroid.data.mapper.OrderMapper
import com.example.decalxeandroid.domain.model.Order
import com.example.decalxeandroid.domain.model.OrderDetail
import com.example.decalxeandroid.domain.model.OrderStageHistory
import com.example.decalxeandroid.domain.model.Result
import com.example.decalxeandroid.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OrderRepositoryImpl(
    private val api: OrderApi,
    private val mapper: OrderMapper
) : OrderRepository {

    override fun getOrders(): Flow<Result<List<Order>>> = flow {
        try {
            val response = api.getOrders()
            if (response.isSuccessful) {
                val orders = response.body()?.map { mapper.toDomain(it) } ?: emptyList()
                emit(Result.Success(orders))
            } else {
                emit(Result.Error("Failed to fetch orders: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Network error: ${e.message}"))
        }
    }

    override fun getOrderById(orderId: String): Flow<Result<Order>> = flow {
        try {
            val response = api.getOrderById(orderId)
            if (response.isSuccessful) {
                val order = response.body()?.let { mapper.toDomain(it) }
                if (order != null) {
                    emit(Result.Success(order))
                } else {
                    emit(Result.Error("Order not found"))
                }
            } else {
                emit(Result.Error("Failed to fetch order: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Network error: ${e.message}"))
        }
    }

    override fun getOrdersByCustomerId(customerId: String): Flow<Result<List<Order>>> = flow {
        try {
            val response = api.getOrders()
            if (response.isSuccessful) {
                val allOrders = response.body()?.map { mapper.toDomain(it) } ?: emptyList()
                val customerOrders = allOrders.filter { it.customerId == customerId }
                emit(Result.Success(customerOrders))
            } else {
                emit(Result.Error("Failed to fetch orders: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Network error: ${e.message}"))
        }
    }

    override fun getOrdersByVehicleId(vehicleId: String): Flow<Result<List<Order>>> = flow {
        try {
            val response = api.getOrders()
            if (response.isSuccessful) {
                val allOrders = response.body()?.map { mapper.toDomain(it) } ?: emptyList()
                val vehicleOrders = allOrders.filter { it.vehicleId == vehicleId }
                emit(Result.Success(vehicleOrders))
            } else {
                emit(Result.Error("Failed to fetch orders: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Network error: ${e.message}"))
        }
    }

    override fun createOrder(order: Order): Flow<Result<Order>> = flow {
        try {
            val createDto = mapper.toCreateDto(order)
            val response = api.createOrder(createDto)
            if (response.isSuccessful) {
                val createdOrder = response.body()?.let { mapper.toDomain(it) }
                if (createdOrder != null) {
                    emit(Result.Success(createdOrder))
                } else {
                    emit(Result.Error("Failed to create order: Invalid response"))
                }
            } else {
                emit(Result.Error("Failed to create order: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Network error: ${e.message}"))
        }
    }

    override fun updateOrder(orderId: String, order: Order): Flow<Result<Order>> = flow {
        try {
            val updateDto = mapper.toUpdateDto(order)
            val response = api.updateOrder(orderId, updateDto)
            if (response.isSuccessful) {
                val updatedOrder = response.body()?.let { mapper.toDomain(it) }
                if (updatedOrder != null) {
                    emit(Result.Success(updatedOrder))
                } else {
                    emit(Result.Error("Failed to update order: Invalid response"))
                }
            } else {
                emit(Result.Error("Failed to update order: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Network error: ${e.message}"))
        }
    }

    override fun deleteOrder(orderId: String): Flow<Result<Boolean>> = flow {
        try {
            val response = api.deleteOrder(orderId)
            if (response.isSuccessful) {
                emit(Result.Success(true))
            } else {
                emit(Result.Error("Failed to delete order: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Network error: ${e.message}"))
        }
    }

    override fun getOrderDetails(orderId: String): Flow<Result<List<OrderDetail>>> = flow {
        try {
            // Placeholder implementation - should call actual API endpoint
            emit(Result.Success(emptyList()))
        } catch (e: Exception) {
            emit(Result.Error("Network error: ${e.message}"))
        }
    }

    override fun getOrderStageHistory(orderId: String): Flow<Result<List<OrderStageHistory>>> = flow {
        try {
            // Placeholder implementation - should call actual API endpoint
            emit(Result.Success(emptyList()))
        } catch (e: Exception) {
            emit(Result.Error("Network error: ${e.message}"))
        }
    }
}
