# TÀI LIỆU ĐẶC TẢ CHỨC NĂNG - DECALXE API

## 📋 Tổng quan dự án

**DecalXeAPI** là hệ thống quản lý dịch vụ dán decal xe máy và ô tô, cung cấp nền tảng quản lý toàn diện cho doanh nghiệp kinh doanh dịch vụ dán decal. Hệ thống bao gồm quản lý khách hàng, đơn hàng, thiết kế, nhân viên, tài chính và báo cáo.

## 👥 Chức năng của từng role

### Role: Admin (Quản trị viên)
**Tài khoản:**
- Đăng nhập, Đăng xuất
- Quản lý tài khoản người dùng
- Phân quyền và vai trò
- Khóa/mở khóa tài khoản

**Trang chủ:**
- Truy cập trang chủ, xem tổng quan
- Dashboard với thống kê toàn hệ thống
- Báo cáo doanh thu và hiệu suất

**Quản lý hệ thống:**
- Quản lý cửa hàng và chi nhánh
- Quản lý vai trò và quyền hạn
- Cấu hình hệ thống
- Backup và khôi phục dữ liệu

**Quản lý nhân viên:**
- Thêm, sửa, xóa nhân viên
- Phân công vai trò
- Theo dõi hiệu suất làm việc
- Quản lý lịch làm việc

**Quản lý dịch vụ:**
- Quản lý danh mục decal
- Thiết lập giá dịch vụ
- Quản lý kho hàng
- Cập nhật danh mục sản phẩm

**Tài chính:**
- Xem báo cáo tài chính tổng hợp
- Quản lý thanh toán và hóa đơn
- Theo dõi doanh thu chi tiết
- Xuất báo cáo tài chính

### Role: Manager (Quản lý)
**Tài khoản:**
- Đăng nhập, Đăng xuất
- Quản lý thông tin cá nhân
- Đổi mật khẩu

**Trang chủ:**
- Truy cập trang chủ, xem tổng quan
- Dashboard quản lý cửa hàng
- Thống kê đơn hàng và doanh thu

**Quản lý đơn hàng:**
- Xem danh sách đơn hàng
- Tạo đơn hàng mới
- Theo dõi tiến độ đơn hàng
- Phê duyệt đơn hàng
- Phân công nhân viên

**Quản lý khách hàng:**
- Xem danh sách khách hàng
- Thêm, sửa thông tin khách hàng
- Quản lý xe của khách hàng
- Theo dõi lịch sử dịch vụ

**Quản lý nhân viên:**
- Xem danh sách nhân viên
- Phân công công việc
- Đánh giá hiệu suất
- Quản lý lịch làm việc

**Báo cáo:**
- Báo cáo doanh thu cửa hàng
- Báo cáo hiệu suất nhân viên
- Báo cáo khách hàng
- Xuất báo cáo

### Role: Sales (Nhân viên bán hàng)
**Tài khoản:**
- Đăng nhập, Đăng xuất
- Quản lý thông tin cá nhân
- Đổi mật khẩu

**Trang chủ:**
- Truy cập trang chủ, xem tổng quan
- Dashboard cá nhân
- Thống kê đơn hàng đã xử lý

**Quản lý đơn hàng:**
- Tạo đơn hàng mới
- Cập nhật thông tin đơn hàng
- Theo dõi tiến độ đơn hàng
- Xử lý yêu cầu khách hàng

**Quản lý khách hàng:**
- Thêm khách hàng mới
- Cập nhật thông tin khách hàng
- Quản lý xe của khách hàng
- Tư vấn dịch vụ

**Dịch vụ:**
- Xem danh mục dịch vụ
- Tư vấn giá dịch vụ
- Đặt lịch hẹn
- Theo dõi lịch sử dịch vụ

### Role: Technician (Thợ dán decal)
**Tài khoản:**
- Đăng nhập, Đăng xuất
- Quản lý thông tin cá nhân
- Đổi mật khẩu

**Trang chủ:**
- Truy cập trang chủ, xem tổng quan
- Dashboard công việc cá nhân
- Thống kê công việc đã hoàn thành

**Quản lý công việc:**
- Xem danh sách công việc được phân công
- Cập nhật tiến độ công việc
- Báo cáo hoàn thành
- Ghi chú và báo cáo vấn đề

**Thiết kế:**
- Xem yêu cầu thiết kế
- Tải xuống file thiết kế
- Cập nhật trạng thái thiết kế
- Gửi phản hồi về thiết kế

**Báo cáo:**
- Báo cáo công việc hàng ngày
- Báo cáo hiệu suất cá nhân
- Ghi chú và đề xuất

### Role: Designer (Thiết kế viên)
**Tài khoản:**
- Đăng nhập, Đăng xuất
- Quản lý thông tin cá nhân
- Đổi mật khẩu

**Trang chủ:**
- Truy cập trang chủ, xem tổng quan
- Dashboard thiết kế
- Thống kê thiết kế đã hoàn thành

**Quản lý thiết kế:**
- Xem yêu cầu thiết kế mới
- Tạo thiết kế mới
- Cập nhật thiết kế
- Phê duyệt thiết kế
- Quản lý template thiết kế

**Thư viện thiết kế:**
- Xem thư viện thiết kế
- Tải lên thiết kế mới
- Quản lý template
- Chia sẻ thiết kế

**Báo cáo:**
- Báo cáo thiết kế đã hoàn thành
- Thống kê hiệu suất thiết kế
- Ghi chú và đề xuất

### Role: Customer (Khách hàng)
**Tài khoản:**
- Đăng nhập, Đăng xuất
- Đăng ký tài khoản
- Quản lý thông tin cá nhân
- Đổi mật khẩu

**Trang chủ:**
- Truy cập trang chủ, xem tổng quan
- Xem thông tin dịch vụ
- Xem tin tức mới nhất

**Quản lý đơn hàng:**
- Xem đơn hàng của mình
- Theo dõi tiến độ đơn hàng
- Xem lịch sử đơn hàng
- Đánh giá dịch vụ

**Quản lý xe:**
- Thêm xe mới
- Cập nhật thông tin xe
- Xem lịch sử dịch vụ xe
- Quản lý biển số xe

**Dịch vụ:**
- Xem danh mục dịch vụ
- Đặt lịch hẹn
- Yêu cầu tư vấn
- Xem báo giá

**Tin tức:**
- Xem tin tức mới nhất
- Xem danh sách tất cả tin tức (cũ và mới)

### Role: Guest (Khách)
**Tin tức:**
- Xem tin tức mới nhất
- Xem thông tin dịch vụ
- Xem thông tin liên hệ

## 🎯 Tính năng chung

### Hệ thống xác thực
- Đăng nhập/Đăng xuất
- Quản lý token (Access Token, Refresh Token)
- Phân quyền theo role
- Bảo mật API

### Quản lý dữ liệu
- CRUD operations cho tất cả entities
- Validation dữ liệu
- Error handling
- Logging và monitoring

### Báo cáo và thống kê
- Dashboard tổng quan
- Báo cáo doanh thu
- Thống kê hiệu suất
- Xuất báo cáo (PDF, Excel)

### Tích hợp hệ thống
- API RESTful
- CORS configuration
- Swagger documentation
- Database migration

## 📊 Cấu trúc dữ liệu chính

### Core Entities
- **Accounts**: Quản lý tài khoản người dùng
- **Roles**: Phân quyền và vai trò
- **Stores**: Quản lý cửa hàng và chi nhánh
- **Employees**: Quản lý nhân viên
- **Customers**: Quản lý khách hàng

### Business Entities
- **Orders**: Quản lý đơn hàng
- **DecalServices**: Dịch vụ decal
- **Designs**: Thiết kế decal
- **Payments**: Thanh toán
- **Warranties**: Bảo hành

### Vehicle Management
- **VehicleBrands**: Hãng xe
- **VehicleModels**: Dòng xe
- **CustomerVehicles**: Xe của khách hàng

## 🔧 Công nghệ sử dụng

### Backend
- **Framework**: ASP.NET Core 8.0
- **Database**: SQL Server/PostgreSQL
- **ORM**: Entity Framework Core
- **Authentication**: JWT Bearer Token
- **Documentation**: Swagger/OpenAPI

### Frontend (React)
- **Framework**: React 18
- **Styling**: Tailwind CSS
- **State Management**: React Query
- **Routing**: React Router
- **UI Components**: Lucide React Icons

## 📱 Giao diện người dùng

### Responsive Design
- Mobile-first approach
- Adaptive layouts
- Touch-friendly interfaces
- Cross-browser compatibility

### User Experience
- Intuitive navigation
- Real-time updates
- Loading states
- Error handling
- Toast notifications

## 🔐 Bảo mật

### Authentication & Authorization
- JWT token-based authentication
- Role-based access control
- Secure password hashing
- Token refresh mechanism

### Data Protection
- Input validation
- SQL injection prevention
- XSS protection
- CORS configuration
- HTTPS enforcement

## 📈 Monitoring & Analytics

### Performance Monitoring
- API response times
- Database query optimization
- Error tracking
- User activity logging

### Business Analytics
- Sales performance
- Customer insights
- Employee productivity
- Operational metrics

---

*Tài liệu này được cập nhật lần cuối: Tháng 1, 2025*



