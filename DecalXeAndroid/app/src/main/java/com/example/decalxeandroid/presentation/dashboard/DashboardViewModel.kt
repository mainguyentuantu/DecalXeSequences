package com.example.decalxeandroid.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.decalxeandroid.domain.model.Order
import com.example.decalxeandroid.domain.repository.CustomerRepository
import com.example.decalxeandroid.domain.repository.CustomerVehicleRepository
import com.example.decalxeandroid.domain.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val vehicleRepository: CustomerVehicleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun loadDashboardData() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                
                var orders = emptyList<Order>()
                var customers = emptyList<com.example.decalxeandroid.domain.model.Customer>()
                var vehicles = emptyList<com.example.decalxeandroid.domain.model.CustomerVehicle>()
                var hasError = false
                var errorMessage = ""
                
                // Load orders
                orderRepository.getOrders().collect { result ->
                    when (result) {
                        is com.example.decalxeandroid.domain.model.Result.Success -> {
                            orders = result.data
                        }
                        is com.example.decalxeandroid.domain.model.Result.Error -> {
                            hasError = true
                            errorMessage = "Lỗi tải đơn hàng: ${result.message}"
                        }
                        else -> {
                            hasError = true
                            errorMessage = "Kết quả đơn hàng không xác định"
                        }
                    }
                }
                
                if (hasError) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = errorMessage
                    )
                    return@launch
                }
                
                // Load customers
                customerRepository.getCustomers().collect { result ->
                    when (result) {
                        is com.example.decalxeandroid.domain.model.Result.Success -> {
                            customers = result.data
                        }
                        is com.example.decalxeandroid.domain.model.Result.Error -> {
                            hasError = true
                            errorMessage = "Lỗi tải khách hàng: ${result.message}"
                        }
                        else -> {
                            hasError = true
                            errorMessage = "Kết quả khách hàng không xác định"
                        }
                    }
                }
                
                if (hasError) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = errorMessage
                    )
                    return@launch
                }
                
                // Load vehicles
                vehicleRepository.getVehicles().collect { result ->
                    when (result) {
                        is com.example.decalxeandroid.domain.model.Result.Success -> {
                            vehicles = result.data
                        }
                        is com.example.decalxeandroid.domain.model.Result.Error -> {
                            hasError = true
                            errorMessage = "Lỗi tải xe: ${result.message}"
                        }
                        else -> {
                            hasError = true
                            errorMessage = "Kết quả xe không xác định"
                        }
                    }
                }
                
                if (hasError) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = errorMessage
                    )
                    return@launch
                }
                
                val totalRevenue = orders.sumOf { it.totalAmount }
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    totalOrders = orders.size,
                    totalCustomers = customers.size,
                    totalVehicles = vehicles.size,
                    totalRevenue = totalRevenue,
                    recentOrders = orders.take(5) // Show recent 5 orders
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Có lỗi xảy ra khi tải dữ liệu dashboard"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class DashboardUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val totalOrders: Int = 0,
    val totalCustomers: Int = 0,
    val totalVehicles: Int = 0,
    val totalRevenue: Double = 0.0,
    val recentOrders: List<Order> = emptyList()
)
