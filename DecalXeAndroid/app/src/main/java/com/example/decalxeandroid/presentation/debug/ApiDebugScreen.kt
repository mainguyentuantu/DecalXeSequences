package com.example.decalxeandroid.presentation.debug

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.decalxeandroid.di.AppContainer
import com.example.decalxeandroid.presentation.vehicles.VehiclesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiDebugScreen() {
    val viewModel: VehiclesViewModel = viewModel {
        VehiclesViewModel(AppContainer.customerVehicleRepository)
    }
    
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "API Debug - Customer Vehicles",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Endpoint Test",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text("URL: https://decalxesequences-production.up.railway.app/api/CustomerVehicles")
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(
                    onClick = { viewModel.loadVehicles() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Test API Call")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Status Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = when {
                    uiState.isLoading -> MaterialTheme.colorScheme.primaryContainer
                    uiState.error != null -> MaterialTheme.colorScheme.errorContainer
                    uiState.vehicles.isNotEmpty() -> MaterialTheme.colorScheme.primaryContainer
                    else -> MaterialTheme.colorScheme.surface
                }
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Status",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                when {
                    uiState.isLoading -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Loading...")
                        }
                    }
                    uiState.error != null -> {
                        Text(
                            text = "❌ Error: ${uiState.error}",
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                    uiState.vehicles.isNotEmpty() -> {
                        Text(
                            text = "✅ Success: ${uiState.vehicles.size} vehicles loaded",
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    else -> {
                        Text("Ready to test...")
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Results
        if (uiState.vehicles.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Vehicles (${uiState.vehicles.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    LazyColumn {
                        items(uiState.vehicles) { vehicle ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Text(
                                        text = "ID: ${vehicle.vehicleID}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text("Chassis: ${vehicle.chassisNumber}")
                                    Text("License: ${vehicle.licensePlate}")
                                    Text("Color: ${vehicle.color}")
                                    Text("Year: ${vehicle.year}")
                                    Text("Model: ${vehicle.vehicleModelName ?: "Chưa có thông tin"}")
                                    Text("Brand: ${vehicle.vehicleBrandName ?: "Chưa có thông tin"}")
                                    Text("Customer: ${vehicle.customerID}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
