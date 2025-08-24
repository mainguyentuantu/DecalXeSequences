package com.example.decalxeandroid.data.repository

import com.example.decalxeandroid.data.api.DecalServiceApi
import com.example.decalxeandroid.data.dto.DecalServiceDto
import com.example.decalxeandroid.data.mapper.DecalServiceMapper
import com.example.decalxeandroid.domain.model.DecalService
import com.example.decalxeandroid.domain.repository.DecalServiceRepository

class DecalServiceRepositoryImpl(
    private val api: DecalServiceApi,
    private val mapper: DecalServiceMapper
) : DecalServiceRepository {

    override suspend fun getServices(page: Int, pageSize: Int): List<DecalService> {
        return try {
            val response = api.getDecalServices()
            if (response.isSuccessful) {
                val services = response.body()?.map { mapper.toDomain(it) } ?: emptyList()
                println("DecalServices API: Successfully loaded ${services.size} services")
                services
            } else {
                val errorBody = response.errorBody()?.string()
                println("DecalServices API Error: ${response.code()} - $errorBody")
                emptyList()
            }
        } catch (e: Exception) {
            println("DecalServices API Exception: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getServiceById(serviceId: String): DecalService? {
        return try {
            val response = api.getDecalServiceById(serviceId)
            if (response.isSuccessful) {
                response.body()?.let { mapper.toDomain(it) }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun createService(service: DecalService): DecalService {
        return try {
            val createDto = com.example.decalxeandroid.data.dto.CreateDecalServiceDto(
                serviceName = service.serviceName,
                description = service.description ?: "",
                price = service.price,
                standardWorkUnits = service.standardWorkUnits ?: 0,
                decalTemplateID = service.decalTemplateId ?: ""
            )
            val response = api.createDecalService(createDto)
            if (response.isSuccessful) {
                response.body()?.let { mapper.toDomain(it) } ?: service
            } else {
                throw Exception("Failed to create service")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateService(serviceId: String, service: DecalService): DecalService {
        return try {
            val updateDto = com.example.decalxeandroid.data.dto.UpdateDecalServiceDto(
                serviceName = service.serviceName,
                description = service.description,
                price = service.price,
                standardWorkUnits = service.standardWorkUnits,
                decalTemplateID = service.decalTemplateId
            )
            val response = api.updateDecalService(serviceId, updateDto)
            if (response.isSuccessful) {
                response.body()?.let { mapper.toDomain(it) } ?: service
            } else {
                throw Exception("Failed to update service")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteService(serviceId: String): Boolean {
        return try {
            val response = api.deleteDecalService(serviceId)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun searchServices(query: String): List<DecalService> {
        return try {
            val services = getServices()
            services.filter { 
                it.serviceName.contains(query, ignoreCase = true) ||
                it.description?.contains(query, ignoreCase = true) == true
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getServicesByType(serviceType: String): List<DecalService> {
        return try {
            val services = getServices()
            services.filter { it.decalTypeName?.equals(serviceType, ignoreCase = true) == true }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
