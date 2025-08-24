package com.example.decalxeandroid.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.decalxeandroid.domain.model.Employee
import com.example.decalxeandroid.domain.repository.EmployeeRepository
// import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
// import javax.inject.Inject

class ProfileViewModel : ViewModel() {
    // Use AppContainer to get repository
    private val employeeRepository: EmployeeRepository = com.example.decalxeandroid.di.AppContainer.employeeRepository

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadEmployees() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                
                val employees = employeeRepository.getEmployees(1, 100)
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    employees = employees
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Có lỗi xảy ra khi tải danh sách nhân viên"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class ProfileUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val employees: List<Employee> = emptyList()
)
