# Android App API Compatibility Fix

## 🔍 Vấn đề gặp phải

App Android đang gửi request đến endpoint `/Auth/register` nhưng nhận được lỗi 404 vì API endpoint thực tế là `/api/Auth/register`.

### Log lỗi từ app Android:
```
POST https://decalxeapi-production.up.railway.app/Auth/register
<-- 404 https://decalxeapi-production.up.railway.app/Auth/register (68ms)
```

## ✅ Giải pháp đã thực hiện

### 1. Tạo AuthMobileController mới
- **File**: `Controllers/AuthController.cs`
- **Mục đích**: Tạo endpoints không có prefix `/api` để tương thích với app Android
- **Route**: `[Route("[controller]")]` thay vì `[Route("api/[controller]")]`

### 2. Tạo RegisterMobileDto mới cho app Android
- **File**: `DTOs/RegisterMobileDto.cs`
- **Mục đích**: Phù hợp với giao diện đăng ký của app Android
- **Fields**: `FullName`, `Username`, `Email`, `Password`, `ConfirmPassword`

### 3. Cập nhật DTOs để khớp với API documentation

#### ChangePasswordRequestDto
```csharp
// Trước
public string OldPassword { get; set; }
public string ConfirmNewPassword { get; set; }

// Sau
public string CurrentPassword { get; set; }
public string ConfirmPassword { get; set; }
```

#### ResetPasswordByUsernameDto
```csharp
// Trước
public string ConfirmNewPassword { get; set; }

// Sau
public string ConfirmPassword { get; set; }
```

### 4. Cập nhật AccountService
- Sửa references từ `request.OldPassword` thành `request.CurrentPassword`
- Sửa references từ `request.ConfirmNewPassword` thành `request.ConfirmPassword`

## 🔗 API Endpoints mới cho app Android

### 1. Register (Cập nhật)
```
POST /Auth/register
```
**Request (RegisterMobileDto):**
```json
{
  "fullName": "Nguyễn Văn A",
  "username": "nguyenvana",
  "email": "nguyenvana@example.com",
  "password": "123456",
  "confirmPassword": "123456"
}
```

**Logic xử lý:**
- Tạo Account với role "Customer" mặc định
- Tạo Customer record với thông tin từ fullName
- Tự động tách FirstName và LastName từ fullName

### 2. Login
```
POST /Auth/login
```
**Request:**
```json
{
  "username": "string",
  "password": "string"
}
```

**Response:**
```json
{
  "accessToken": "string",
  "refreshToken": "string",
  "user": {
    "accountID": "string",
    "username": "string",
    "email": "string",
    "role": "string",
    "accountRoleName": "string",
    "isActive": true,
    "employeeID": "string"
  }
}
```

### 3. Refresh Token
```
POST /Auth/refresh-token
```
**Request:**
```json
{
  "accessToken": "string",
  "refreshToken": "string"
}
```

### 4. Logout
```
POST /Auth/logout
```
**Request:**
```json
{
  "refreshToken": "string"
}
```

### 5. Change Password
```
PUT /Auth/change-password
```
**Request:**
```json
{
  "currentPassword": "string",
  "newPassword": "string",
  "confirmPassword": "string"
}
```

### 6. Reset Password
```
POST /Auth/reset-password-by-username
```
**Request:**
```json
{
  "username": "string",
  "newPassword": "string",
  "confirmPassword": "string"
}
```

## 🔄 Cả hai endpoint đều hoạt động

### Web App (React)
- Sử dụng: `/api/Auth/*`
- DTO: `RegisterDto` (username, password, roleID)
- Ví dụ: `/api/Auth/login`, `/api/Auth/register`

### Android App
- Sử dụng: `/Auth/*`
- DTO: `RegisterMobileDto` (fullName, username, email, password, confirmPassword)
- Ví dụ: `/Auth/login`, `/Auth/register`

## 🧪 Testing

### Test với Postman/curl
```bash
# Test register endpoint cho Android
curl -X POST https://decalxeapi-production.up.railway.app/Auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Nguyễn Văn A",
    "username": "nguyenvana",
    "email": "nguyenvana@example.com",
    "password": "123456",
    "confirmPassword": "123456"
  }'

# Test login endpoint cho Android
curl -X POST https://decalxeapi-production.up.railway.app/Auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "nguyenvana",
    "password": "123456"
  }'
```

## 📝 Lưu ý quan trọng

1. **Backward Compatibility**: Cả hai endpoint đều hoạt động để đảm bảo tương thích
2. **Same Logic**: Cả hai controller đều sử dụng cùng logic và services
3. **Security**: Cả hai endpoint đều có cùng security policies
4. **Validation**: Cả hai endpoint đều sử dụng cùng validation rules
5. **Auto Customer Creation**: App Android tự động tạo Customer record khi đăng ký

## 🚀 Deployment

Sau khi deploy, app Android sẽ có thể:
- ✅ Đăng ký tài khoản với đầy đủ thông tin (fullName, email)
- ✅ Tự động tạo Customer record
- ✅ Đăng nhập và nhận access token
- ✅ Refresh token khi cần
- ✅ Đăng xuất an toàn
- ✅ Đổi mật khẩu
- ✅ Reset mật khẩu

## 🔧 Cấu hình app Android

App Android cần gửi request với format mới:

1. Base URL: `https://decalxeapi-production.up.railway.app`
2. Endpoint: `/Auth/register` (không có `/api`)
3. Content-Type: `application/json`
4. Request body format:
```json
{
  "fullName": "Họ và tên đầy đủ",
  "username": "tên đăng nhập",
  "email": "email@example.com",
  "password": "mật khẩu",
  "confirmPassword": "xác nhận mật khẩu"
}
```

## 🎯 Lợi ích của giải pháp

1. **Phù hợp với UI**: Giao diện app Android không cần thay đổi
2. **Tự động tạo Customer**: Không cần đăng ký riêng Customer
3. **Validation đầy đủ**: Kiểm tra email, xác nhận mật khẩu
4. **Tương thích ngược**: Web app vẫn hoạt động bình thường
