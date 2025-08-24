# 🚀 QUICK DEBUG GUIDE - HTTP 404 ORDER DETAIL

## 📱 Cách Debug Lỗi 404

### Step 1: Check Logs trong Android Studio
Khi click vào order và gặp lỗi 404, check console logs:

```
OrdersScreen: Clicking order with ID: 'ACTUAL_ID_HERE'
DashboardScreen: Navigation to OrderDetail requested with orderId: 'ACTUAL_ID_HERE'
DashboardScreen: Generated route: 'order_detail/ACTUAL_ID_HERE'
OrderDetailViewModel: Starting to load order with ID: 'ACTUAL_ID_HERE'
OrderRepository: Full URL will be: https://decalxesequences-production.up.railway.app/api/Orders/ACTUAL_ID_HERE
OrderRepository: Response code: 404
```

### Step 2: Kiểm tra OrderID
**Xem log để xác định**:
- OrderID có format gì? (UUID, số, string?)
- Có bị trống không?
- Có ký tự đặc biệt không?

### Step 3: Test API Manual
**Dùng Postman test**:
```
GET https://decalxesequences-production.up.railway.app/api/Orders/ACTUAL_ID_FROM_LOG
```

**Kết quả có thể**:
- ✅ 200 OK → App có vấn đề, API OK
- ❌ 404 Not Found → Database thiếu record
- ❌ 401 Unauthorized → Thiếu auth
- ❌ 500 Server Error → Backend lỗi

## 🔧 Giải pháp nhanh

### Nếu OrderID sai format:
1. Check OrderDto.orderID mapping
2. Check database actual ID format
3. Verify Orders list API trả về đúng ID

### Nếu Database thiếu data:
1. Check orders list có data không
2. Verify FK constraints
3. Add missing records to database

### Nếu API endpoint sai:
1. Check Backend controller route
2. Verify API documentation
3. Check Base URL trong AppContainer

## 📋 UI Error Display

App sẽ hiển thị:
```
🔴 [Error Icon]
Đơn hàng không tồn tại (ID: abc-123)
OrderID được truyền: abc-123
[Thử lại] Button
```

**Debug info** hiển thị exact OrderID được truyền từ navigation.

## ⚡ Quick Fixes Applied

1. **✅ Detailed Logging**: Every step logged
2. **✅ HTTP Response Handling**: Proper 404/401/500 handling  
3. **✅ UI Error Display**: Shows exact OrderID
4. **✅ Validation**: Check empty/invalid OrderID
5. **✅ Friendly Messages**: User-friendly error text

**Result**: HTTP 404 issues can now be quickly identified and resolved!
