package com.example.decalxeandroid.presentation.vehicles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.decalxeandroid.domain.model.CustomerVehicle
import com.example.decalxeandroid.domain.model.Order
import com.example.decalxeandroid.domain.repository.CustomerVehicleRepository
import com.example.decalxeandroid.domain.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class VehicleDetailViewModel(
    private val vehicleId: String,
    private val customerVehicleRepository: CustomerVehicleRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<VehicleDetailUiState>(VehicleDetailUiState.Loading)
    val uiState: StateFlow<VehicleDetailUiState> = _uiState.asStateFlow()
    
    init {
        loadVehicle()
    }
    
    fun loadVehicle() {
        viewModelScope.launch {
            _uiState.value = VehicleDetailUiState.Loading
            
            try {
                // Load vehicle details
                val vehicleResult = customerVehicleRepository.getVehicleById(vehicleId)
                vehicleResult.collect { result ->
                    when (result) {
                        is com.example.decalxeandroid.domain.model.Result.Success -> {
                            val vehicle = result.data
                            
                            // Load vehicle orders
                            val ordersFlow = orderRepository.getOrdersByVehicleId(vehicleId)
                            ordersFlow.collect { ordersResult ->
                                when (ordersResult) {
                                    is com.example.decalxeandroid.domain.model.Result.Success -> {
                                        val orders = ordersResult.data
                                        _uiState.value = VehicleDetailUiState.Success(
                                            vehicle = vehicle,
                                            orders = orders
                                        )
                                    }
                                    is com.example.decalxeandroid.domain.model.Result.Error -> {
                                        _uiState.value = VehicleDetailUiState.Error(
                                            "Không thể tải danh sách đơn hàng: ${ordersResult.message}"
                                        )
                                    }
                                    else -> {
                                        _uiState.value = VehicleDetailUiState.Error(
                                            "Kết quả đơn hàng không xác định"
                                        )
                                    }
                                }
                            }
                        }
                        is com.example.decalxeandroid.domain.model.Result.Error -> {
                            _uiState.value = VehicleDetailUiState.Error(
                                "Không thể tải thông tin xe: ${result.message}"
                            )
                        }
                        else -> {
                            _uiState.value = VehicleDetailUiState.Error(
                                "Kết quả xe không xác định"
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = VehicleDetailUiState.Error(
                    "Lỗi không xác định: ${e.message}"
                )
            }
        }
    }
    
    fun editVehicle() {
        // TODO: Navigate to edit vehicle screen
    }
    
    fun deleteVehicle() {
        viewModelScope.launch {
            try {
                val result = customerVehicleRepository.deleteVehicle(vehicleId)
                result.collect { deleteResult ->
                    when (deleteResult) {
                        is com.example.decalxeandroid.domain.model.Result.Success -> {
                            // TODO: Navigate back to vehicles list
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

sealed class VehicleDetailUiState {
    object Loading : VehicleDetailUiState()
    data class Success(
        val vehicle: CustomerVehicle,
        val orders: List<Order>
    ) : VehicleDetailUiState()
    data class Error(val message: String) : VehicleDetailUiState()
}
