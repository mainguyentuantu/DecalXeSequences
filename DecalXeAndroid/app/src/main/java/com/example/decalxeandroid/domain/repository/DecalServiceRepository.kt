package com.example.decalxeandroid.domain.repository

import com.example.decalxeandroid.domain.model.DecalService

interface DecalServiceRepository {
    suspend fun getServices(page: Int = 1, pageSize: Int = 20): List<DecalService>
    suspend fun getServiceById(serviceId: String): DecalService?
    suspend fun createService(service: DecalService): DecalService
    suspend fun updateService(serviceId: String, service: DecalService): DecalService
    suspend fun deleteService(serviceId: String): Boolean
    suspend fun searchServices(query: String): List<DecalService>
    suspend fun getServicesByType(serviceType: String): List<DecalService>
}
