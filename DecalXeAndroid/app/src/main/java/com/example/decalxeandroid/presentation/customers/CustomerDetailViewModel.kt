package com.example.decalxeandroid.presentation.customers

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
            
            try {
                // Load customer details
                val customerResult = customerRepository.getCustomerById(customerId)
                customerResult.collect { result ->
                    when (result) {
                        is com.example.decalxeandroid.domain.model.Result.Success -> {
                            val customer = result.data
                            
                            // Load customer vehicles
                            val vehiclesFlow = customerVehicleRepository.getVehiclesByCustomerId(customerId)
                            vehiclesFlow.collect { vehiclesResult ->
                                when (vehiclesResult) {
                                    is com.example.decalxeandroid.domain.model.Result.Success -> {
                                        val vehicles = vehiclesResult.data
                                        
                                        // Load customer orders
                                        val ordersFlow = orderRepository.getOrdersByCustomerId(customerId)
                                        ordersFlow.collect { ordersResult ->
                                            when (ordersResult) {
                                                is com.example.decalxeandroid.domain.model.Result.Success -> {
                                                    val orders = ordersResult.data
                                                    _uiState.value = CustomerDetailUiState.Success(
                                                        customer = customer,
                                                        vehicles = vehicles,
                                                        orders = orders
                                                    )
                                                }
                                                is com.example.decalxeandroid.domain.model.Result.Error -> {
                                                    _uiState.value = CustomerDetailUiState.Error(
                                                        "Không thể tải danh sách đơn hàng: ${ordersResult.message}"
                                                    )
                                                }
                                                else -> {
                                                    _uiState.value = CustomerDetailUiState.Error(
                                                        "Kết quả đơn hàng không xác định"
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    is com.example.decalxeandroid.domain.model.Result.Error -> {
                                        _uiState.value = CustomerDetailUiState.Error(
                                            "Không thể tải danh sách xe: ${vehiclesResult.message}"
                                        )
                                    }
                                    else -> {
                                        _uiState.value = CustomerDetailUiState.Error(
                                            "Kết quả xe không xác định"
                                        )
                                    }
                                }
                            }
                        }
                        is com.example.decalxeandroid.domain.model.Result.Error -> {
                            _uiState.value = CustomerDetailUiState.Error(
                                "Không thể tải thông tin khách hàng: ${result.message}"
                            )
                        }
                        else -> {
                            _uiState.value = CustomerDetailUiState.Error(
                                "Kết quả khách hàng không xác định"
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = CustomerDetailUiState.Error(
                    "Lỗi không xác định: ${e.message}"
                )
            }
        }
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
