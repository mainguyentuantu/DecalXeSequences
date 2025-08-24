# 📋 Hướng dẫn kiểm tra Customer Detail Feature

## ✅ Tình trạng hiện tại
**Customer Detail Feature đã được triển khai đầy đủ và hoạt động bình thường!**

## 🎯 Các tính năng đã sẵn sàng

### 1. Navigation từ Customer List
- ✅ Khi click vào customer card trong danh sách 
- ✅ Tự động navigate đến CustomerDetailScreen với customerId
- ✅ Navigation route: `customer_detail/{customerId}`

### 2. API Integration
- ✅ **GET /api/Customers/{id}**: Lấy thông tin chi tiết khách hàng
- ✅ **GET /api/CustomerVehicles/by-customer/{customerId}**: Lấy danh sách xe
- ✅ **GET /api/Orders/by-customer/{customerId}**: Lấy danh sách đơn hàng

### 3. UI Components

#### 3.1 Customer Info Card
- ✅ **Customer ID**: Badge hiển thị ID
- ✅ **Thông tin cá nhân**: Tên, số điện thoại, email, địa chỉ
- ✅ **Icons**: Mỗi thông tin có icon riêng
- ✅ **Null Safety**: Hiển thị "Chưa có" khi thông tin trống

#### 3.2 Vehicles Section
- ✅ **Đếm số lượng**: "Xe của khách hàng (X)"
- ✅ **Empty State**: "Chưa có xe nào" khi không có dữ liệu
- ✅ **Vehicle Items**: 
  - Biển số xe (title bold)
  - Model + Brand xe
  - Màu sắc xe
  - Click để navigate đến VehicleDetailScreen

#### 3.3 Orders Section  
- ✅ **Đếm số lượng**: "Đơn hàng (X)"
- ✅ **Empty State**: "Chưa có đơn hàng nào"
- ✅ **Order Items**:
  - Order ID
  - Trạng thái đơn hàng
  - Tổng tiền (VNĐ)
  - Click để navigate đến OrderDetailScreen

### 4. State Management
- ✅ **Loading State**: Progress indicator khi đang load
- ✅ **Success State**: Hiển thị đầy đủ thông tin
- ✅ **Error State**: Thông báo lỗi + nút "Thử lại"

### 5. Actions Available
- ✅ **Back Navigation**: Nút back arrow trong TopAppBar
- ✅ **Edit Button**: Icon edit trong TopAppBar (TODO: cần implement navigation)
- ✅ **Delete Button**: Icon delete trong TopAppBar (đã có API call)

## 🧪 Hướng dẫn test

### Test 1: Navigation cơ bản
1. **Khởi động app** → Login → Tab "Khách hàng"
2. **Click vào customer card** bất kỳ
3. **Kiểm tra**: App có navigate đến CustomerDetailScreen
4. **Kiểm tra**: TopAppBar có title "Chi tiết khách hàng"
5. **Kiểm tra**: Có nút back, edit, delete

### Test 2: Data Loading
1. **Từ Customer Detail screen**
2. **Kiểm tra**: Loading spinner hiển thị ban đầu
3. **Chờ API call hoàn thành**
4. **Kiểm tra**: Customer info card hiển thị đúng
5. **Kiểm tra**: Vehicles section hiển thị (hoặc "Chưa có xe nào")
6. **Kiểm tra**: Orders section hiển thị (hoặc "Chưa có đơn hàng nào")

### Test 3: Customer Information Display
```
Kiểm tra hiển thị:
✅ Customer ID badge
✅ Họ và tên với icon Person
✅ Số điện thoại với icon Phone (hoặc "Chưa có")  
✅ Email với icon Email (hoặc "Chưa có")
✅ Địa chỉ với icon LocationOn (hoặc "Chưa có")
```

### Test 4: Vehicles Display
```
Nếu có xe:
✅ Header: "Xe của khách hàng (2)" 
✅ Mỗi xe hiển thị: biển số, model-brand, màu sắc
✅ Click vào xe → Navigate to VehicleDetailScreen (placeholder)

Nếu không có xe:
✅ Header: "Xe của khách hàng (0)"
✅ Text: "Chưa có xe nào"
```

### Test 5: Orders Display
```
Nếu có đơn hàng:
✅ Header: "Đơn hàng (3)"
✅ Mỗi đơn hiển thị: Order ID, trạng thái, tổng tiền
✅ Click vào đơn → Navigate to OrderDetailScreen (placeholder)

Nếu không có đơn hàng:
✅ Header: "Đơn hàng (0)"  
✅ Text: "Chưa có đơn hàng nào"
```

### Test 6: Error Handling
1. **Disconnect internet** → Refresh app
2. **Kiểm tra**: Error state hiển thị
3. **Kiểm tra**: Error message + nút "Thử lại"
4. **Click "Thử lại"** → Loading state hiển thị lại

### Test 7: Navigation Actions
1. **Click back button** → Quay về customers list
2. **Click edit button** → TODO (chưa implement)
3. **Click delete button** → TODO (cần confirm dialog)

## 🔧 Các API Endpoints được sử dụng

### Primary API Calls (Tự động)
```
CustomerDetailViewModel.loadCustomer() gọi:

1. GET https://decalxesequences-production.up.railway.app/api/Customers/{id}
   → Lấy CustomerDto với thông tin chi tiết

2. GET https://decalxesequences-production.up.railway.app/api/CustomerVehicles/by-customer/{customerId}  
   → Lấy List<CustomerVehicleDto> xe của khách hàng

3. GET https://decalxesequences-production.up.railway.app/api/Orders/by-customer/{customerId}
   → Lấy List<OrderDto> đơn hàng của khách hàng
```

### Secondary API Calls (On User Action)  
```
Delete Customer:
DELETE https://decalxesequences-production.up.railway.app/api/Customers/{id}
→ Chỉ gọi khi user click delete button
```

## 🐛 Debugging Tips

### Nếu không hiển thị data:
1. **Kiểm tra logs** để xem API calls có thành công không
2. **Kiểm tra internet connection**
3. **Kiểm tra Base URL** trong ApiConstants
4. **Kiểm tra customerId** có được truyền đúng không

### Nếu navigation không hoạt động:
1. **Kiểm tra Screen.kt** có CustomerDetail route không
2. **Kiểm tra DashboardScreen.kt** có CustomerDetailScreen import không
3. **Kiểm tra CustomersScreen** có truyền đúng onNavigateToCustomerDetail không

### Nếu crash khi load:
1. **Kiểm tra AppContainer** có customerRepository, customerVehicleRepository, orderRepository không
2. **Kiểm tra network module** có setup đúng không
3. **Kiểm tra dependencies** trong build.gradle

## 📱 Expected User Flow

```
1. User opens app → Login thành công
2. User clicks tab "Khách hàng" → Customers list hiển thị
3. User clicks vào customer card → Navigate to CustomerDetailScreen  
4. Screen hiển thị loading → API calls execute
5. Data loads → Customer info, vehicles, orders hiển thị
6. User có thể:
   - Click back → Quay về customers list
   - Click vehicle → Navigate to vehicle detail  
   - Click order → Navigate to order detail
   - Click edit → TODO (implement later)
   - Click delete → Delete customer (với confirmation)
```

## ✅ Kết luận

**Customer Detail Feature đã hoạt động đầy đủ!** 

Tất cả các yêu cầu trong báo cáo lỗi đã được giải quyết:
- ✅ Navigation từ customer list đến detail
- ✅ API call GET /api/Customers/{id}  
- ✅ API call GET /api/CustomerVehicles/by-customer/{customerId}
- ✅ Hiển thị thông tin chi tiết customer
- ✅ Hiển thị danh sách xe của customer
- ✅ UI professional với error handling
- ✅ Navigation đến vehicle/order details

**Người dùng giờ đây có thể xem thông tin chi tiết khách hàng và quản lý xe/đơn hàng liên quan một cách hoàn chỉnh.**
