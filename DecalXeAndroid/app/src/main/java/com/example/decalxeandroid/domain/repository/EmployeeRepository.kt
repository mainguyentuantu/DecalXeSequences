package com.example.decalxeandroid.domain.repository

import com.example.decalxeandroid.domain.model.Employee

interface EmployeeRepository {
    suspend fun getEmployees(page: Int = 1, pageSize: Int = 20): List<Employee>
    suspend fun getEmployeeById(employeeId: String): Employee?
    suspend fun createEmployee(employee: Employee): Employee
    suspend fun updateEmployee(employeeId: String, employee: Employee): Employee
    suspend fun deleteEmployee(employeeId: String): Boolean
    suspend fun getEmployeesByRole(role: String): List<Employee>
    suspend fun searchEmployees(query: String): List<Employee>
    suspend fun getEmployeeByPhone(phoneNumber: String): Employee?
}



