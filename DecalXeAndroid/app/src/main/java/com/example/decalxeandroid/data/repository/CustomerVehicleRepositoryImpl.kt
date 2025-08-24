package com.example.decalxeandroid.data.repository

import com.example.decalxeandroid.data.api.CustomerVehicleApi
import com.example.decalxeandroid.data.dto.CustomerVehicleDto
import com.example.decalxeandroid.data.mapper.CustomerVehicleMapper
import com.example.decalxeandroid.domain.model.CustomerVehicle
import com.example.decalxeandroid.domain.model.Result
import com.example.decalxeandroid.domain.repository.CustomerVehicleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CustomerVehicleRepositoryImpl(
    private val api: CustomerVehicleApi,
    private val mapper: CustomerVehicleMapper
) : CustomerVehicleRepository {

    override fun getVehicles(): Flow<Result<List<CustomerVehicle>>> = flow {
        try {
            val response = api.getCustomerVehicles()
            if (response.isSuccessful) {
                val vehicles = response.body()?.map { mapper.toDomain(it) } ?: emptyList()
                emit(Result.Success(vehicles))
            } else {
                val errorBody = response.errorBody()?.string()
                emit(Result.Error("Failed to fetch vehicles: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Network error: ${e.message}"))
        }
    }

    override fun getVehicleById(vehicleId: String): Flow<Result<CustomerVehicle>> = flow {
        try {
            val response = api.getCustomerVehicleById(vehicleId)
            if (response.isSuccessful) {
                val vehicle = response.body()?.let { mapper.toDomain(it) }
                if (vehicle != null) {
                    emit(Result.Success(vehicle))
                } else {
                    emit(Result.Error("Vehicle not found"))
                }
            } else {
                emit(Result.Error("Failed to fetch vehicle: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Network error: ${e.message}"))
        }
    }

    override fun getVehiclesByCustomerId(customerId: String): Flow<Result<List<CustomerVehicle>>> = flow {
        try {
            val response = api.getCustomerVehicles()
            if (response.isSuccessful) {
                val allVehicles = response.body()?.map { mapper.toDomain(it) } ?: emptyList()
                val customerVehicles = allVehicles.filter { it.customerID == customerId }
                emit(Result.Success(customerVehicles))
            } else {
                emit(Result.Error("Failed to fetch vehicles: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Network error: ${e.message}"))
        }
    }

    override fun createVehicle(vehicle: CustomerVehicle): Flow<Result<CustomerVehicle>> = flow {
        try {
            val dto = mapper.toCreateDto(vehicle)
            val response = api.createCustomerVehicle(dto)
            if (response.isSuccessful) {
                val createdVehicle = response.body()?.let { mapper.toDomain(it) }
                if (createdVehicle != null) {
                    emit(Result.Success(createdVehicle))
                } else {
                    emit(Result.Error("Failed to create vehicle: Invalid response"))
                }
            } else {
                emit(Result.Error("Failed to create vehicle: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Network error: ${e.message}"))
        }
    }

    override fun updateVehicle(vehicleId: String, vehicle: CustomerVehicle): Flow<Result<CustomerVehicle>> = flow {
        try {
            val dto = mapper.toUpdateDto(vehicle)
            val response = api.updateCustomerVehicle(vehicleId, dto)
            if (response.isSuccessful) {
                val updatedVehicle = response.body()?.let { mapper.toDomain(it) }
                if (updatedVehicle != null) {
                    emit(Result.Success(updatedVehicle))
                } else {
                    emit(Result.Error("Failed to update vehicle: Invalid response"))
                }
            } else {
                emit(Result.Error("Failed to update vehicle: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Network error: ${e.message}"))
        }
    }

    override fun deleteVehicle(vehicleId: String): Flow<Result<Boolean>> = flow {
        try {
            val response = api.deleteCustomerVehicle(vehicleId)
            if (response.isSuccessful) {
                emit(Result.Success(true))
            } else {
                emit(Result.Error("Failed to delete vehicle: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Network error: ${e.message}"))
        }
    }
}
