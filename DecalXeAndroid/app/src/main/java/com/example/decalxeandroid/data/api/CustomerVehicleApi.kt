package com.example.decalxeandroid.data.api

import com.example.decalxeandroid.data.dto.CustomerVehicleDto
import com.example.decalxeandroid.data.dto.CreateCustomerVehicleDto
import com.example.decalxeandroid.data.dto.UpdateCustomerVehicleDto
import retrofit2.Response
import retrofit2.http.*

interface CustomerVehicleApi {
    
    @GET("customer-vehicles")
    suspend fun getCustomerVehicles(): Response<List<CustomerVehicleDto>>
    
    @GET("customer-vehicles/{id}")
    suspend fun getCustomerVehicleById(@Path("id") id: String): Response<CustomerVehicleDto>
    
    @POST("customer-vehicles")
    suspend fun createCustomerVehicle(@Body vehicle: CreateCustomerVehicleDto): Response<CustomerVehicleDto>
    
    @PUT("customer-vehicles/{id}")
    suspend fun updateCustomerVehicle(
        @Path("id") id: String, 
        @Body vehicle: UpdateCustomerVehicleDto
    ): Response<CustomerVehicleDto>
    
    @DELETE("customer-vehicles/{id}")
    suspend fun deleteCustomerVehicle(@Path("id") id: String): Response<Unit>
}
