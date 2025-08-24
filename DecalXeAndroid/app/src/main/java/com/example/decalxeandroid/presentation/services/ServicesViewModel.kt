package com.example.decalxeandroid.presentation.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.decalxeandroid.domain.model.DecalService
import com.example.decalxeandroid.domain.repository.DecalServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ServicesViewModel(
    private val serviceRepository: DecalServiceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ServicesUiState())
    val uiState: StateFlow<ServicesUiState> = _uiState.asStateFlow()

    fun loadServices() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                
                val services = serviceRepository.getServices()
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    services = services
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Có lỗi xảy ra khi tải danh sách dịch vụ"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class ServicesUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val services: List<DecalService> = emptyList()
)
