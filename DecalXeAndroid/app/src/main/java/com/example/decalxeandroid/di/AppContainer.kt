package com.example.decalxeandroid.di

import com.example.decalxeandroid.data.api.CustomerApi
import com.example.decalxeandroid.data.api.CustomerVehicleApi
import com.example.decalxeandroid.data.api.DecalServiceApi
import com.example.decalxeandroid.data.api.EmployeeApi
import com.example.decalxeandroid.data.api.OrderApi
import com.example.decalxeandroid.data.mapper.CustomerMapper
import com.example.decalxeandroid.data.mapper.CustomerVehicleMapper
import com.example.decalxeandroid.data.mapper.DecalServiceMapper
import com.example.decalxeandroid.data.mapper.EmployeeMapper
import com.example.decalxeandroid.data.mapper.OrderMapper
import com.example.decalxeandroid.data.repository.CustomerRepositoryImpl
import com.example.decalxeandroid.data.repository.CustomerVehicleRepositoryImpl
import com.example.decalxeandroid.data.repository.DecalServiceRepositoryImpl
import com.example.decalxeandroid.data.repository.EmployeeRepositoryImpl
import com.example.decalxeandroid.data.repository.OrderRepositoryImpl
import com.example.decalxeandroid.domain.repository.CustomerRepository
import com.example.decalxeandroid.domain.repository.CustomerVehicleRepository
import com.example.decalxeandroid.domain.repository.DecalServiceRepository
import com.example.decalxeandroid.domain.repository.EmployeeRepository
import com.example.decalxeandroid.domain.repository.OrderRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppContainer {
    
    // Base URL for API
    private const val BASE_URL = "https://decalxeapi-production.up.railway.app/api/"
    
    // Retrofit instance
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    // API Services
    private val customerApi: CustomerApi by lazy {
        retrofit.create(CustomerApi::class.java)
    }
    
    private val customerVehicleApi: CustomerVehicleApi by lazy {
        retrofit.create(CustomerVehicleApi::class.java)
    }
    
    private val employeeApi: EmployeeApi by lazy {
        retrofit.create(EmployeeApi::class.java)
    }
    
    private val orderApi: OrderApi by lazy {
        retrofit.create(OrderApi::class.java)
    }
    
    private val decalServiceApi: DecalServiceApi by lazy {
        retrofit.create(DecalServiceApi::class.java)
    }
    
    // Mappers
    private val customerMapper: CustomerMapper by lazy {
        CustomerMapper()
    }
    
    private val customerVehicleMapper: CustomerVehicleMapper by lazy {
        CustomerVehicleMapper()
    }
    
    private val employeeMapper: EmployeeMapper by lazy {
        EmployeeMapper()
    }
    
    private val orderMapper: OrderMapper by lazy {
        OrderMapper()
    }
    
    private val decalServiceMapper: DecalServiceMapper by lazy {
        DecalServiceMapper()
    }
    
    // Repositories
    val customerRepository: CustomerRepository by lazy {
        CustomerRepositoryImpl(customerApi, customerMapper)
    }
    
    val customerVehicleRepository: CustomerVehicleRepository by lazy {
        CustomerVehicleRepositoryImpl(customerVehicleApi, customerVehicleMapper)
    }
    
    val employeeRepository: EmployeeRepository by lazy {
        EmployeeRepositoryImpl(employeeApi, employeeMapper)
    }
    
    val orderRepository: OrderRepository by lazy {
        OrderRepositoryImpl(orderApi, orderMapper)
    }
    
    val decalServiceRepository: DecalServiceRepository by lazy {
        DecalServiceRepositoryImpl(decalServiceApi, decalServiceMapper)
    }
}
