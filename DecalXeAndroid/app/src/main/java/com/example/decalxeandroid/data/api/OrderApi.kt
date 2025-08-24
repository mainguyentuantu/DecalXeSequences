package com.example.decalxeandroid.data.api

import com.example.decalxeandroid.data.dto.OrderDto
import com.example.decalxeandroid.data.dto.CreateOrderDto
import com.example.decalxeandroid.data.dto.UpdateOrderDto
import retrofit2.Response
import retrofit2.http.*

interface OrderApi {
    
    @GET("orders")
    suspend fun getOrders(): Response<List<OrderDto>>
    
    @GET("orders/{id}")
    suspend fun getOrderById(@Path("id") id: String): Response<OrderDto>
    
    @POST("orders")
    suspend fun createOrder(@Body order: CreateOrderDto): Response<OrderDto>
    
    @PUT("orders/{id}")
    suspend fun updateOrder(
        @Path("id") id: String, 
        @Body order: UpdateOrderDto
    ): Response<OrderDto>
    
    @DELETE("orders/{id}")
    suspend fun deleteOrder(@Path("id") id: String): Response<Unit>
}
