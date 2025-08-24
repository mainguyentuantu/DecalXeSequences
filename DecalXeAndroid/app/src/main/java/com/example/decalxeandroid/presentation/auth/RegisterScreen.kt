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
// Removed problematic imports for now
import androidx.compose.foundation.clickable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.decalxeandroid.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val viewModel = remember { MockRegisterViewModel() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var selectedRoleID by remember { mutableStateOf("Customer") }
    var expanded by remember { mutableStateOf(false) }
    
    val roleOptions = listOf("Customer", "Employee", "Admin")
    
    LaunchedEffect(uiState) {
        when (uiState) {
            is RegisterUiState.Success -> {
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
            text = "Đăng ký",
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
        
        // Role Selection - Simple Dropdown
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedRoleID,
                onValueChange = {},
                readOnly = true,
                label = { Text("Vai trò") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Invisible clickable overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { expanded = true }
            )
        }
        
        // Dropdown Menu
        if (expanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column {
                    roleOptions.forEach { role ->
                        TextButton(
                            onClick = {
                                selectedRoleID = role
                                expanded = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = role,
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Xác nhận mật khẩu") },
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
            onClick = { 
                if (password == confirmPassword) {
                    viewModel.register(username, password, selectedRoleID)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = username.isNotBlank() && 
                     password.isNotBlank() && 
                     confirmPassword.isNotBlank() && 
                     selectedRoleID.isNotBlank() &&
                     password == confirmPassword &&
                     uiState !is RegisterUiState.Loading
        ) {
            if (uiState is RegisterUiState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Đăng ký")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(
            onClick = onNavigateToLogin
        ) {
            Text("Đã có tài khoản? Đăng nhập")
        }
        
        if (uiState is RegisterUiState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            val errorState = uiState as RegisterUiState.Error
            Text(
                text = errorState.message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        
        if (password.isNotBlank() && confirmPassword.isNotBlank() && password != confirmPassword) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Mật khẩu không khớp",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// UI State classes moved to RegisterViewModel.kt to avoid duplication

// Mock implementation for now
class MockRegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Initial)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()
    
    fun register(username: String, password: String, roleID: String) {
        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            
            // Simulate network delay
            delay(1000)
            
            // Mock validation
            if (username.isBlank() || password.isBlank()) {
                _uiState.value = RegisterUiState.Error("Vui lòng nhập đầy đủ thông tin")
                return@launch
            }
            
            if (password.length < 6) {
                _uiState.value = RegisterUiState.Error("Mật khẩu phải có ít nhất 6 ký tự")
                return@launch
            }
            
            // Mock successful registration
            _uiState.value = RegisterUiState.Success(
                com.example.decalxeandroid.domain.model.User(
                    accountId = "1",
                    username = username,
                    email = "user@example.com",
                    fullName = "User",
                    phoneNumber = null,
                    role = when (roleID.lowercase()) {
                        "admin" -> com.example.decalxeandroid.domain.model.UserRole.ADMIN
                        "employee" -> com.example.decalxeandroid.domain.model.UserRole.SALES
                        else -> com.example.decalxeandroid.domain.model.UserRole.CUSTOMER
                    },
                    isActive = true,
                    createdAt = "2024-01-01",
                    lastLoginAt = null
                )
            )
        }
    }
    
    fun resetState() {
        _uiState.value = RegisterUiState.Initial
    }
}
