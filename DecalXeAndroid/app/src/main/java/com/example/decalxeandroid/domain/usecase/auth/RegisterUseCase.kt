package com.example.decalxeandroid.domain.usecase.auth

import com.example.decalxeandroid.domain.model.AuthResult
import com.example.decalxeandroid.domain.repository.AuthRepository

class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        username: String, password: String, roleID: String
    ): AuthResult {
        if (username.isBlank()) {
            return AuthResult.Error("Username không được để trống")
        }
        if (password.length < 6) {
            return AuthResult.Error("Mật khẩu phải có ít nhất 6 ký tự")
        }
        if (roleID.isBlank()) {
            return AuthResult.Error("Role ID không được để trống")
        }
        
        return authRepository.register(username, password, roleID)
    }
}
