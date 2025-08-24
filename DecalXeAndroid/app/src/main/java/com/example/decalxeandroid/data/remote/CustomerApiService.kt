package com.example.decalxeandroid.data.remote

import com.example.decalxeandroid.data.dto.CustomerDto
import com.example.decalxeandroid.data.dto.CreateCustomerDto
import com.example.decalxeandroid.data.dto.UpdateCustomerDto
import retrofit2.http.*

interface CustomerApiService {
    @GET(ApiConstants.CUSTOMERS_ENDPOINT)
    suspend fun getCustomers(
        @Query("pageNumber") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): List<CustomerDto>
    
    @GET(ApiConstants.CUSTOMER_BY_ID_ENDPOINT)
    suspend fun getCustomerById(@Path("id") customerId: String): CustomerDto
    
    @POST(ApiConstants.CUSTOMERS_ENDPOINT)
    suspend fun createCustomer(@Body customer: CreateCustomerDto): CustomerDto
    
    @PUT(ApiConstants.CUSTOMER_BY_ID_ENDPOINT)
    suspend fun updateCustomer(
        @Path("id") customerId: String,
        @Body customer: UpdateCustomerDto
    ): CustomerDto
    
    @DELETE(ApiConstants.CUSTOMER_BY_ID_ENDPOINT)
    suspend fun deleteCustomer(@Path("id") customerId: String): String
    
    @GET(ApiConstants.CUSTOMER_SEARCH_ENDPOINT)
    suspend fun searchCustomers(@Query("query") query: String): List<CustomerDto>
    
    @GET(ApiConstants.CUSTOMER_BY_PHONE_ENDPOINT)
    suspend fun getCustomerByPhone(@Path("phoneNumber") phoneNumber: String): CustomerDto?
    
    @GET(ApiConstants.CUSTOMER_BY_EMAIL_ENDPOINT)
    suspend fun getCustomerByEmail(@Path("email") email: String): CustomerDto?
}
