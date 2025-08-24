package com.example.decalxeandroid.presentation.customers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.decalxeandroid.domain.model.Customer
import com.example.decalxeandroid.domain.model.CustomerVehicle
import com.example.decalxeandroid.di.AppContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDetailScreen(
    customerId: String,
    onNavigateBack: () -> Unit,
    onNavigateToVehicle: (String) -> Unit,
    onNavigateToOrder: (String) -> Unit,
    viewModel: CustomerDetailViewModel = viewModel(
        factory = CustomerDetailViewModelFactory(
            customerId = customerId,
            customerRepository = AppContainer.customerRepository,
            customerVehicleRepository = AppContainer.customerVehicleRepository,
            orderRepository = AppContainer.orderRepository
        )
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chi tiết khách hàng") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.editCustomer() }) {
                        Icon(Icons.Default.Edit, contentDescription = "Chỉnh sửa")
                    }
                    IconButton(onClick = { viewModel.deleteCustomer() }) {
                        Icon(Icons.Default.Delete, contentDescription = "Xóa", tint = Color.Red)
                    }
                }
            )
        }
    ) { paddingValues ->
        when (uiState) {
            is CustomerDetailUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is CustomerDetailUiState.Success -> {
                val customer = (uiState as CustomerDetailUiState.Success).customer
                val vehicles = (uiState as CustomerDetailUiState.Success).vehicles
                val orders = (uiState as CustomerDetailUiState.Success).orders
                
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Customer Info Section
                    item {
                        CustomerInfoCard(customer = customer)
                    }
                    
                    // Vehicles Section
                    item {
                        VehiclesSection(
                            vehicles = vehicles,
                            onVehicleClick = onNavigateToVehicle
                        )
                    }
                    
                    // Orders Section
                    item {
                        OrdersSection(
                            orders = orders,
                            onOrderClick = onNavigateToOrder
                        )
                    }
                }
            }
            is CustomerDetailUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = (uiState as CustomerDetailUiState.Error).message,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Red
                        )
                        Button(onClick = { viewModel.loadCustomer() }) {
                            Text("Thử lại")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CustomerInfoCard(customer: Customer) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Thông tin khách hàng",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "ID: ${customer.customerId}",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            
            Divider()
            
            InfoRow(
                icon = Icons.Default.Person,
                label = "Họ và tên",
                value = customer.fullName
            )
            
            InfoRow(
                icon = Icons.Default.Phone,
                label = "Số điện thoại",
                value = customer.phoneNumber ?: "Chưa có"
            )
            
            InfoRow(
                icon = Icons.Default.Email,
                label = "Email",
                value = customer.email ?: "Chưa có"
            )
            
            InfoRow(
                icon = Icons.Default.LocationOn,
                label = "Địa chỉ",
                value = customer.address ?: "Chưa có"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VehiclesSection(
    vehicles: List<CustomerVehicle>,
    onVehicleClick: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Xe của khách hàng (${vehicles.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            if (vehicles.isEmpty()) {
                Text(
                    text = "Chưa có xe nào",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                vehicles.forEach { vehicle ->
                    VehicleItem(
                        vehicle = vehicle,
                        onClick = { onVehicleClick(vehicle.vehicleID) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VehicleItem(
    vehicle: CustomerVehicle,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = vehicle.licensePlate,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${vehicle.vehicleModelName} - ${vehicle.vehicleBrandName}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Màu: ${vehicle.color}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = "Xem chi tiết",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrdersSection(
    orders: List<com.example.decalxeandroid.domain.model.Order>,
    onOrderClick: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Đơn hàng (${orders.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            if (orders.isEmpty()) {
                Text(
                    text = "Chưa có đơn hàng nào",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                orders.forEach { order ->
                    OrderItem(
                        order = order,
                        onClick = { onOrderClick(order.orderId) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderItem(
    order: com.example.decalxeandroid.domain.model.Order,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Đơn hàng #${order.orderId}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Trạng thái: ${order.orderStatus}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Tổng tiền: ${order.totalAmount} VNĐ",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = "Xem chi tiết",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
