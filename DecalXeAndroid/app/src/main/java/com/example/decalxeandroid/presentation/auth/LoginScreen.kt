package com.example.decalxeandroid.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.decalxeandroid.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
// Removed problematic imports for now

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val viewModel = remember { MockLoginViewModel() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUiState.Success -> {
                onNavigateToHome()
            }
            else -> {}
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Đăng nhập",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Tên đăng nhập") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = { viewModel.login(username, password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = username.isNotBlank() && password.isNotBlank() && uiState !is LoginUiState.Loading
        ) {
            if (uiState is LoginUiState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Đăng nhập")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(
            onClick = onNavigateToRegister
        ) {
            Text("Chưa có tài khoản? Đăng ký ngay")
        }
        
        if (uiState is LoginUiState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            val errorState = uiState as LoginUiState.Error
            Text(
                text = errorState.message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// UI State classes moved to LoginViewModel.kt to avoid duplication

// Mock implementation for now
class MockLoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Initial)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    
    fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            
            // Simulate network delay
            delay(1000)
            
            // Mock validation
            if (username.isBlank() || password.isBlank()) {
                _uiState.value = LoginUiState.Error("Vui lòng nhập đầy đủ thông tin")
                return@launch
            }
            
            // Mock successful login
            _uiState.value = LoginUiState.Success(
                com.example.decalxeandroid.domain.model.User(
                    accountId = "1",
                    username = username,
                    email = "user@example.com",
                    fullName = "User",
                    phoneNumber = null,
                    role = com.example.decalxeandroid.domain.model.UserRole.CUSTOMER,
                    isActive = true,
                    createdAt = "2024-01-01",
                    lastLoginAt = null
                )
            )
        }
    }
    
    fun resetState() {
        _uiState.value = LoginUiState.Initial
    }
}
