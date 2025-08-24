package com.example.decalxeandroid.domain.usecase.auth

import com.example.decalxeandroid.domain.model.AuthResult
import com.example.decalxeandroid.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): AuthResult {
        if (username.isBlank()) {
            return AuthResult.Error("Username không được để trống")
        }
        if (password.isBlank()) {
            return AuthResult.Error("Mật khẩu không được để trống")
        }
        
        return authRepository.login(username, password)
    }
}
