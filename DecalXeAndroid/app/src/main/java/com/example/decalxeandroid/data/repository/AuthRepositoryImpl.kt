package com.example.decalxeandroid.data.repository

import com.example.decalxeandroid.data.dto.*
import com.example.decalxeandroid.data.mapper.AuthMapper
import com.example.decalxeandroid.data.remote.AuthApiService
import com.example.decalxeandroid.domain.model.AuthResult
import com.example.decalxeandroid.domain.model.User
import com.example.decalxeandroid.domain.repository.AuthRepository
import com.example.decalxeandroid.data.local.TokenManager
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val tokenManager: TokenManager
) : AuthRepository {
    
    override suspend fun login(username: String, password: String): AuthResult {
        return try {
            val loginRequest = LoginRequestDto(username, password)
            val response = authApiService.login(loginRequest)
            
            // Save tokens
            tokenManager.saveTokens(response.accessToken, response.refreshToken)
            
            // Map user data
            val user = AuthMapper.mapUserDataDtoToUser(response.user)
            
            AuthResult.Success(user, response.accessToken, response.refreshToken)
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Đăng nhập thất bại")
        }
    }
    
    override suspend fun register(username: String, password: String, roleID: String): AuthResult {
        return try {
            val registerRequest = RegisterRequestDto(username, password, roleID)
            authApiService.register(registerRequest)
            AuthResult.Success(User("", username, "", "", null, com.example.decalxeandroid.domain.model.UserRole.CUSTOMER, true, "", null), "", "")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Đăng ký thất bại")
        }
    }
    
    override suspend fun changePassword(oldPassword: String, newPassword: String): AuthResult {
        return try {
            val changePasswordRequest = ChangePasswordRequestDto(oldPassword, newPassword, newPassword) // Use newPassword as confirmPassword
            authApiService.changePassword(changePasswordRequest)
            AuthResult.Success(User("", "", "", "", null, com.example.decalxeandroid.domain.model.UserRole.CUSTOMER, true, "", null), "", "")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Đổi mật khẩu thất bại")
        }
    }
    
    override suspend fun resetPassword(username: String): AuthResult {
        return try {
            val resetPasswordRequest = ResetPasswordRequestDto(username, "", "") // Empty strings for newPassword and confirmPassword
            authApiService.resetPassword(resetPasswordRequest)
            AuthResult.Success(User("", "", "", "", null, com.example.decalxeandroid.domain.model.UserRole.CUSTOMER, true, "", null), "", "")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Đặt lại mật khẩu thất bại")
        }
    }
    
    override suspend fun logout() {
        tokenManager.clearTokens()
    }
    
    override suspend fun refreshToken(): AuthResult {
        return try {
            val refreshToken = tokenManager.getRefreshToken()
            if (refreshToken == null) {
                return AuthResult.Error("Không tìm thấy refresh token")
            }
            
            val refreshTokenRequest = RefreshTokenRequestDto(refreshToken)
            val response = authApiService.refreshToken(refreshTokenRequest)
            
            // Save new tokens
            tokenManager.saveTokens(response.accessToken, response.refreshToken)
            
            // Map user data
            val user = AuthMapper.mapUserDataDtoToUser(response.user)
            
            AuthResult.Success(user, response.accessToken, response.refreshToken)
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Làm mới token thất bại")
        }
    }
    
    override suspend fun getCurrentUser(): User? {
        // This would typically be stored locally or retrieved from the API
        // For now, return null as we need to implement local storage
        return null
    }
    
    override suspend fun isLoggedIn(): Boolean {
        return tokenManager.getAccessToken() != null
    }
    
    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        tokenManager.saveTokens(accessToken, refreshToken)
    }
    
    override suspend fun getAccessToken(): String? {
        return tokenManager.getAccessToken()
    }
    
    override suspend fun getRefreshToken(): String? {
        return tokenManager.getRefreshToken()
    }
    
    override suspend fun clearTokens() {
        tokenManager.clearTokens()
    }
}
