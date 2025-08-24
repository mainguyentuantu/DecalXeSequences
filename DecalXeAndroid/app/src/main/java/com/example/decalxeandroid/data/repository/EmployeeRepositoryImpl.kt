package com.example.decalxeandroid.data.repository

import com.example.decalxeandroid.data.api.EmployeeApi
import com.example.decalxeandroid.data.dto.EmployeeDto
import com.example.decalxeandroid.data.mapper.EmployeeMapper
import com.example.decalxeandroid.domain.model.Employee
import com.example.decalxeandroid.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EmployeeRepositoryImpl(
    private val api: EmployeeApi,
    private val mapper: EmployeeMapper
) : EmployeeRepository {

    override suspend fun getEmployees(page: Int, pageSize: Int): List<Employee> {
        return try {
            val response = api.getEmployees()
            if (response.isSuccessful) {
                response.body()?.map { mapper.toDomain(it) } ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getEmployeeById(employeeId: String): Employee? {
        return try {
            val response = api.getEmployeeById(employeeId)
            if (response.isSuccessful) {
                response.body()?.let { mapper.toDomain(it) }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun createEmployee(employee: Employee): Employee {
        return try {
            val createDto = com.example.decalxeandroid.data.dto.CreateEmployeeDto(
                firstName = employee.firstName,
                lastName = employee.lastName,
                phoneNumber = employee.phoneNumber ?: "",
                email = employee.email ?: "",
                address = employee.address ?: "",
                storeID = employee.storeId ?: "",
                accountID = employee.accountId ?: ""
            )
            val response = api.createEmployee(createDto)
            if (response.isSuccessful) {
                response.body()?.let { mapper.toDomain(it) } ?: employee
            } else {
                throw Exception("Failed to create employee")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateEmployee(employeeId: String, employee: Employee): Employee {
        return try {
            val updateDto = com.example.decalxeandroid.data.dto.UpdateEmployeeDto(
                firstName = employee.firstName,
                lastName = employee.lastName,
                phoneNumber = employee.phoneNumber,
                email = employee.email,
                address = employee.address,
                storeID = employee.storeId,
                accountID = employee.accountId
            )
            val response = api.updateEmployee(employeeId, updateDto)
            if (response.isSuccessful) {
                response.body()?.let { mapper.toDomain(it) } ?: employee
            } else {
                throw Exception("Failed to update employee")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteEmployee(employeeId: String): Boolean {
        return try {
            val response = api.deleteEmployee(employeeId)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getEmployeesByRole(role: String): List<Employee> {
        return try {
            val employees = getEmployees()
            employees.filter { it.accountRoleName?.equals(role, ignoreCase = true) == true }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun searchEmployees(query: String): List<Employee> {
        return try {
            val employees = getEmployees()
            employees.filter { 
                it.firstName.contains(query, ignoreCase = true) ||
                it.lastName.contains(query, ignoreCase = true) ||
                it.email?.contains(query, ignoreCase = true) == true ||
                it.phoneNumber?.contains(query, ignoreCase = true) == true
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getEmployeeByPhone(phoneNumber: String): Employee? {
        return try {
            val employees = getEmployees()
            employees.find { it.phoneNumber == phoneNumber }
        } catch (e: Exception) {
            null
        }
    }
}
