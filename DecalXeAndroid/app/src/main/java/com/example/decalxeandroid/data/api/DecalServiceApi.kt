package com.example.decalxeandroid.data.api

import com.example.decalxeandroid.data.dto.DecalServiceDto
import com.example.decalxeandroid.data.dto.CreateDecalServiceDto
import com.example.decalxeandroid.data.dto.UpdateDecalServiceDto
import retrofit2.Response
import retrofit2.http.*

interface DecalServiceApi {
    
    @GET("services")
    suspend fun getDecalServices(): Response<List<DecalServiceDto>>
    
    @GET("services/{id}")
    suspend fun getDecalServiceById(@Path("id") id: String): Response<DecalServiceDto>
    
    @POST("services")
    suspend fun createDecalService(@Body service: CreateDecalServiceDto): Response<DecalServiceDto>
    
    @PUT("services/{id}")
    suspend fun updateDecalService(
        @Path("id") id: String, 
        @Body service: UpdateDecalServiceDto
    ): Response<DecalServiceDto>
    
    @DELETE("services/{id}")
    suspend fun deleteDecalService(@Path("id") id: String): Response<Unit>
}
