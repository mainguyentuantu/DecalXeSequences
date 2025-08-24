# Phase 2 - Kiến trúc ứng dụng Android DecalXe

## 🏗️ KIẾN TRÚC ỨNG DỤNG ANDROID

### 📋 Phân tích Backend API

Dựa trên phân tích backend API, hệ thống DecalXe có các module chính:

#### 🔐 Authentication & Authorization
- **AuthController**: Login, Register, Change Password, Reset Password
- **AccountsController**: Quản lý tài khoản, phân quyền
- **RolesController**: Quản lý vai trò (Admin, Manager, Sales, Technician, Customer)

#### 👥 Customer Management
- **CustomersController**: CRUD khách hàng
- **CustomerVehiclesController**: Quản lý xe khách hàng (với biển số)
- **VehicleBrandsController**: Thương hiệu xe
- **VehicleModelsController**: Model xe

#### 📋 Order Management
- **OrdersController**: CRUD đơn hàng
- **OrderDetailsController**: Chi tiết đơn hàng
- **OrderStageHistoriesController**: Lịch sử trạng thái đơn hàng

#### 🎨 Decal Services
- **DecalServicesController**: Dịch vụ decal
- **DecalTypesController**: Loại decal
- **DecalTemplatesController**: Template decal
- **DesignsController**: Thiết kế decal

#### 💰 Payment & Warranty
- **PaymentsController**: Thanh toán
- **DepositsController**: Đặt cọc
- **WarrantiesController**: Bảo hành
- **FeedbacksController**: Đánh giá

#### 👨‍💼 Employee Management
- **EmployeesController**: Quản lý nhân viên
- **StoresController**: Quản lý cửa hàng
- **TechLaborPricesController**: Giá nhân công

### 🎯 Kiến trúc được đề xuất: Clean Architecture + MVVM

```
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                       │
├─────────────────────────────────────────────────────────────┤
│  UI (Compose) │ ViewModels │ Navigation │ Theme/Components  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                     DOMAIN LAYER                            │
├─────────────────────────────────────────────────────────────┤
│ Use Cases │ Entities │ Repository Interfaces │ Business Logic│
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                      DATA LAYER                             │
├─────────────────────────────────────────────────────────────┤
│ Repositories │ API Services │ Local Storage │ Mappers       │
└─────────────────────────────────────────────────────────────┘
```

### 📱 Các tính năng chính của ứng dụng Android

#### 👤 Customer App (Ứng dụng khách hàng)
1. **Authentication & Account Management**
   - Đăng ký/Đăng nhập
   - Quản lý thông tin cá nhân
   - Đổi mật khẩu
   - Quên mật khẩu

2. **Vehicle Management**
   - Thêm/sửa thông tin xe
   - Danh sách xe của tôi
   - Lịch sử dịch vụ theo xe
   - Tìm kiếm xe theo biển số

3. **Order Management**
   - Tạo đơn hàng mới
   - Chọn dịch vụ decal
   - Theo dõi tiến độ đơn hàng
   - Lịch sử đơn hàng
   - Chi tiết đơn hàng

4. **Decal Services**
   - Xem catalog decal
   - Xem giá dịch vụ
   - Đặt lịch hẹn
   - Xem template decal

5. **Payment & Warranty**
   - Xem hóa đơn
   - Thông tin bảo hành
   - Feedback đánh giá
   - Lịch sử thanh toán

#### 👨‍💼 Staff App (Ứng dụng nhân viên)
1. **Dashboard theo vai trò**
   - Sales: Quản lý khách hàng, đơn hàng
   - Technician: Cập nhật tiến độ, checklist
   - Manager: Báo cáo, thống kê

2. **Order Management**
   - Danh sách đơn hàng được giao
   - Cập nhật trạng thái
   - Ghi chú tiến độ
   - Quản lý stage history

3. **Customer Management**
   - Thông tin khách hàng
   - Lịch sử giao dịch
   - Tạo đơn hàng cho khách
   - Quản lý xe khách hàng

4. **Service Management**
   - Quản lý dịch vụ decal
   - Quản lý template
   - Quản lý giá cả
   - Quản lý bảo hành

### 🎨 Thiết kế UI/UX - Các màn hình chính

#### 📱 Customer App Screens:
1. **Authentication Screens**
   - Login Screen
   - Register Screen
   - Forgot Password Screen
   - Change Password Screen

2. **Main Navigation**
   - Home Dashboard
   - My Vehicles
   - My Orders
   - Services
   - Profile

3. **Vehicle Screens**
   - Vehicle List
   - Add/Edit Vehicle
   - Vehicle Details
   - Service History

4. **Order Screens**
   - Order List
   - Create Order
   - Order Details
   - Order Tracking
   - Order History

5. **Service Screens**
   - Service Catalog
   - Service Details
   - Price List
   - Template Gallery

6. **Payment Screens**
   - Invoice List
   - Invoice Details
   - Payment History
   - Warranty Info

#### 👨‍💼 Staff App Screens:
1. **Authentication & Role Selection**
   - Login Screen
   - Role Selection
   - Dashboard per Role

2. **Order Management**
   - Order List (filtered by role)
   - Order Details
   - Update Order Status
   - Add Order Notes

3. **Customer Management**
   - Customer List
   - Customer Details
   - Create Customer
   - Customer Vehicles

4. **Service Management**
   - Service List
   - Service Details
   - Template Management
   - Price Management

### 🔧 Công nghệ và thư viện

#### 🛠️ Core Technologies:
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM + Clean Architecture
- **Navigation**: Navigation Compose
- **State Management**: StateFlow, SharedFlow

#### 📚 Libraries:
- **Networking**: Retrofit + OkHttp
- **JSON**: Gson + Kotlinx Serialization
- **Image Loading**: Coil
- **Local Storage**: Room Database + DataStore
- **Dependency Injection**: Hilt (sau khi fix compatibility)
- **Logging**: Timber
- **Testing**: MockK, Turbine, JUnit

#### 🔌 API Integration:
- **Base URL**: `https://decalxeapi-production.up.railway.app/api/`
- **Authentication**: JWT Token (Access + Refresh)
- **Error Handling**: Global error handling
- **Offline Support**: Room database caching

### 🚀 Kế hoạch tích hợp API

#### 🔌 API Integration Strategy:

**Phase 1: Authentication APIs**
- Login/Register
- Token management
- Role-based access

**Phase 2: Core Business APIs**
- Customer management
- Vehicle management
- Order management
- Service catalog

**Phase 3: Advanced Features**
- Payment integration
- Push notifications
- Offline sync
- Analytics

#### 🛡️ API Security Implementation:
- JWT token storage (encrypted)
- Automatic token refresh
- Role-based UI rendering
- Secure API communication

### 📅 Giai đoạn phát triển

#### 🚀 Phase 2: Core Features (Tuần 4-6)
**Week 4: Authentication & Navigation**
- Implement authentication screens
- Setup role-based navigation
- Implement token management
- Add offline support

**Week 5: Customer & Vehicle Management**
- Customer profile screens
- Vehicle management screens
- API integration for customer/vehicle
- Local caching implementation

**Week 6: Order Management**
- Order creation flow
- Order tracking screens
- Order history
- Service selection

#### 🚀 Phase 3: Advanced Features (Tuần 7-9)
**Week 7: Service Catalog & Templates**
- Service catalog screens
- Template gallery
- Price display
- Service booking

**Week 8: Payment & Warranty**
- Payment screens
- Invoice management
- Warranty information
- Feedback system

**Week 9: Staff App Features**
- Role-based dashboards
- Order management for staff
- Customer management for staff
- Service management

### 📊 Timeline chi tiết

#### 📅 6-Week Development Timeline:

**Week 4 (Authentication & Navigation)**
- [ ] Authentication UI screens
- [ ] Token management
- [ ] Role-based navigation
- [ ] Offline support setup

**Week 5 (Customer & Vehicle Management)**
- [ ] Customer profile screens
- [ ] Vehicle management screens
- [ ] API integration
- [ ] Local caching

**Week 6 (Order Management)**
- [ ] Order creation flow
- [ ] Order tracking
- [ ] Order history
- [ ] Service selection

### 🛠️ Công cụ và Workflow phát triển

#### 🔧 Development Tools:
- **IDE**: Android Studio Hedgehog
- **Version Control**: Git
- **API Testing**: Postman
- **Design**: Figma (UI/UX)
- **Project Management**: GitHub Issues

#### 📱 Build Variants:
- **Debug**: Development với logging
- **Release**: Production build
- **Customer**: Customer app variant
- **Staff**: Staff app variant

### 💰 Ước tính chi phí và nguồn lực

#### 👥 Team Requirements:
- **Android Developer**: 1 người (6 tuần)
- **UI/UX Designer**: 0.5 người (2 tuần)
- **Backend Support**: 0.25 người (6 tuần)

#### 💳 Estimated Costs:
- **Development**: $12,000 - $18,000
- **Design**: $2,000 - $3,000
- **Testing**: $1,000 - $2,000
- **Total**: $15,000 - $23,000

### 📋 Rủi ro và giải pháp

#### ⚠️ Technical Risks:
1. **Hilt Compatibility Issues**
   - **Risk**: Dependency injection problems
   - **Solution**: Manual DI hoặc fix Hilt versions

2. **API Integration Complexity**
   - **Risk**: Complex backend integration
   - **Solution**: Phased approach, thorough testing

3. **Offline Sync Complexity**
   - **Risk**: Data synchronization issues
   - **Solution**: Robust conflict resolution

### 🎯 Kết luận và bước tiếp theo

Kế hoạch Phase 2 cung cấp:
✅ Kiến trúc rõ ràng với Clean Architecture + MVVM
✅ Timeline chi tiết 6 tuần với các milestone cụ thể
✅ Phân tích đầy đủ backend API và tích hợp
✅ Thiết kế UI/UX cho cả Customer và Staff apps
✅ Ước tính chi phí và nguồn lực cần thiết
✅ Quản lý rủi ro và giải pháp phòng ngừa

**Bước tiếp theo ngay lập tức:**
1. Setup project structure theo Clean Architecture
2. Implement authentication screens
3. Setup navigation với role-based access
4. Begin API integration
5. Start UI development
