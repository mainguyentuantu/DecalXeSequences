package com.example.decalxeandroid.presentation.customers

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.decalxeandroid.domain.model.Customer
import com.example.decalxeandroid.domain.model.CustomerVehicle
import com.example.decalxeandroid.domain.model.Order
import com.example.decalxeandroid.domain.repository.CustomerRepository
import com.example.decalxeandroid.domain.repository.CustomerVehicleRepository
import com.example.decalxeandroid.domain.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class CustomerDetailViewModel(
    private val customerId: String,
    private val customerRepository: CustomerRepository,
    private val customerVehicleRepository: CustomerVehicleRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<CustomerDetailUiState>(CustomerDetailUiState.Loading)
    val uiState: StateFlow<CustomerDetailUiState> = _uiState.asStateFlow()
    
    init {
        loadCustomer()
    }
    
    fun loadCustomer() {
        viewModelScope.launch {
            _uiState.value = CustomerDetailUiState.Loading
            Log.d(TAG, "Loading customer details for ID: $customerId")
            
            try {
                // Load customer details
                Log.d(TAG, "Fetching customer data...")
                val customerResult = customerRepository.getCustomerById(customerId)
                customerResult.collect { result ->
                    when (result) {
                        is com.example.decalxeandroid.domain.model.Result.Success -> {
                            val customer = result.data
                            Log.d(TAG, "Successfully loaded customer: ${customer.fullName}")
                            
                            // Load customer vehicles and orders concurrently
                            loadVehiclesAndOrders(customer)
                        }
                        is com.example.decalxeandroid.domain.model.Result.Error -> {
                            Log.e(TAG, "Failed to load customer: ${result.message}")
                            _uiState.value = CustomerDetailUiState.Error(
                                "Không thể tải thông tin khách hàng: ${result.message}"
                            )
                        }
                        else -> {
                            Log.e(TAG, "Unknown customer result type")
                            _uiState.value = CustomerDetailUiState.Error(
                                "Kết quả khách hàng không xác định"
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception loading customer", e)
                _uiState.value = CustomerDetailUiState.Error(
                    "Lỗi không xác định: ${e.message}"
                )
            }
        }
    }
    
    private suspend fun loadVehiclesAndOrders(customer: Customer) {
        var vehicles = emptyList<CustomerVehicle>()
        var orders = emptyList<Order>()
        var vehiclesLoaded = false
        var ordersLoaded = false
        
        // Load vehicles
        val vehiclesFlow = customerVehicleRepository.getVehiclesByCustomerId(customerId)
        vehiclesFlow.collect { vehiclesResult ->
            when (vehiclesResult) {
                is com.example.decalxeandroid.domain.model.Result.Success -> {
                    vehicles = vehiclesResult.data
                    vehiclesLoaded = true
                    Log.d(TAG, "Successfully loaded ${vehicles.size} vehicles for customer")
                }
                is com.example.decalxeandroid.domain.model.Result.Error -> {
                    Log.w(TAG, "Failed to load vehicles: ${vehiclesResult.message}")
                    vehicles = emptyList()
                    vehiclesLoaded = true
                }
                else -> {
                    Log.w(TAG, "Unknown vehicles result type")
                    vehicles = emptyList()
                    vehiclesLoaded = true
                }
            }
            
            // Update UI if both are loaded
            if (vehiclesLoaded && ordersLoaded) {
                _uiState.value = CustomerDetailUiState.Success(
                    customer = customer,
                    vehicles = vehicles,
                    orders = orders
                )
            }
        }
        
        // Load orders
        val ordersFlow = orderRepository.getOrdersByCustomerId(customerId)
        ordersFlow.collect { ordersResult ->
            when (ordersResult) {
                is com.example.decalxeandroid.domain.model.Result.Success -> {
                    orders = ordersResult.data
                    ordersLoaded = true
                    Log.d(TAG, "Successfully loaded ${orders.size} orders for customer")
                }
                is com.example.decalxeandroid.domain.model.Result.Error -> {
                    Log.w(TAG, "Failed to load orders: ${ordersResult.message}")
                    orders = emptyList()
                    ordersLoaded = true
                }
                else -> {
                    Log.w(TAG, "Unknown orders result type")
                    orders = emptyList()
                    ordersLoaded = true
                }
            }
            
            // Update UI if both are loaded
            if (vehiclesLoaded && ordersLoaded) {
                _uiState.value = CustomerDetailUiState.Success(
                    customer = customer,
                    vehicles = vehicles,
                    orders = orders
                )
            }
        }
    }
    
    companion object {
        private const val TAG = "CustomerDetailViewModel"
    }
    
    fun editCustomer() {
        // TODO: Navigate to edit customer screen
    }
    
    fun deleteCustomer() {
        viewModelScope.launch {
            try {
                val result = customerRepository.deleteCustomer(customerId)
                result.collect { deleteResult ->
                    when (deleteResult) {
                        is com.example.decalxeandroid.domain.model.Result.Success -> {
                            // TODO: Navigate back to customers list
                        }
                        is com.example.decalxeandroid.domain.model.Result.Error -> {
                            // TODO: Show error message
                        }
                        else -> {
                            // TODO: Show error message
                        }
                    }
                }
            } catch (e: Exception) {
                // TODO: Show error message
            }
        }
    }
}

sealed class CustomerDetailUiState {
    object Loading : CustomerDetailUiState()
    data class Success(
        val customer: Customer,
        val vehicles: List<CustomerVehicle>,
        val orders: List<Order>
    ) : CustomerDetailUiState()
    data class Error(val message: String) : CustomerDetailUiState()
}
