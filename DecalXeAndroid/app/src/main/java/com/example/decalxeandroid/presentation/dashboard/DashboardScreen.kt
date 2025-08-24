package com.example.decalxeandroid.presentation.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.decalxeandroid.presentation.navigation.Screen
import com.example.decalxeandroid.presentation.customers.CustomersScreen
import com.example.decalxeandroid.presentation.orders.OrdersScreen
import com.example.decalxeandroid.presentation.vehicles.VehiclesScreen
import com.example.decalxeandroid.presentation.services.ServicesScreen
import com.example.decalxeandroid.presentation.profile.ProfileScreen
import com.example.decalxeandroid.presentation.debug.ApiDebugScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToLogin: () -> Unit
) {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            DashboardBottomNavigation(
                navController = navController,
                onNavigateToLogin = onNavigateToLogin
            )
        }
    ) { paddingValues ->
        DashboardNavHost(
            navController = navController,
            onNavigateToLogin = onNavigateToLogin,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun DashboardBottomNavigation(
    navController: NavHostController,
    onNavigateToLogin: () -> Unit
) {
    val screens = listOf(
        DashboardBottomNavItem(
            route = Screen.Dashboard.route,
            title = "Dashboard",
            icon = Icons.Default.Dashboard
        ),
        DashboardBottomNavItem(
            route = Screen.Customers.route,
            title = "Khách hàng",
            icon = Icons.Default.People
        ),
        DashboardBottomNavItem(
            route = Screen.Orders.route,
            title = "Đơn hàng",
            icon = Icons.Default.ShoppingCart
        ),
        DashboardBottomNavItem(
            route = Screen.Vehicles.route,
            title = "Xe",
            icon = Icons.Default.DirectionsCar
        ),
        DashboardBottomNavItem(
            route = Screen.Services.route,
            title = "Dịch vụ",
            icon = Icons.Default.Build
        ),
        DashboardBottomNavItem(
            route = Screen.Profile.route,
            title = "Hồ sơ",
            icon = Icons.Default.Person
        ),
        DashboardBottomNavItem(
            route = Screen.ApiDebug.route,
            title = "Debug",
            icon = Icons.Default.Settings
        )
    )
    
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        
        screens.forEach { screen ->
            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title
                    )
                },
                label = { Text(screen.title) },
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun DashboardNavHost(
    navController: NavHostController,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route,
        modifier = modifier
    ) {
        composable(Screen.Dashboard.route) {
            DashboardHomeScreen()
        }
        composable(Screen.Customers.route) {
            CustomersScreen(
                onNavigateToCustomerDetail = { customerId ->
                    navController.navigate(Screen.CustomerDetail.createRoute(customerId))
                },
                onNavigateToAddCustomer = {
                    navController.navigate(Screen.AddCustomer.route)
                }
            )
        }
        composable(Screen.Orders.route) {
            OrdersScreen(
                onNavigateToOrderDetail = { orderId ->
                    navController.navigate(Screen.OrderDetail.createRoute(orderId))
                },
                onNavigateToCreateOrder = {
                    navController.navigate(Screen.CreateOrder.route)
                }
            )
        }
        composable(Screen.Vehicles.route) {
            VehiclesScreen(
                onNavigateToVehicleDetail = { vehicleId ->
                    navController.navigate(Screen.VehicleDetail.createRoute(vehicleId))
                },
                onNavigateToAddVehicle = {
                    navController.navigate(Screen.AddVehicle.route)
                }
            )
        }
        composable(Screen.Services.route) {
            ServicesScreen(
                onNavigateToCreateService = {
                    navController.navigate(Screen.CreateService.route)
                }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigateToLogin = onNavigateToLogin
            )
        }
        composable(Screen.ApiDebug.route) {
            ApiDebugScreen()
        }
        
        // Detail screens
        composable(
            route = Screen.CustomerDetail.route,
            arguments = listOf(
                navArgument("customerId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val customerId = backStackEntry.arguments?.getString("customerId") ?: ""
            // Placeholder screen
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Chi tiết khách hàng: $customerId")
            }
        }
        
        composable(
            route = Screen.OrderDetail.route,
            arguments = listOf(
                navArgument("orderId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
            // Placeholder screen
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Chi tiết đơn hàng: $orderId")
            }
        }
        
        composable(
            route = Screen.VehicleDetail.route,
            arguments = listOf(
                navArgument("vehicleId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId") ?: ""
            // Placeholder screen
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Chi tiết xe: $vehicleId")
            }
        }
        
        // Create/Add screens
        composable(Screen.AddCustomer.route) {
            // Placeholder screen
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Thêm khách hàng")
            }
        }
        
        composable(Screen.CreateOrder.route) {
            // Placeholder screen
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Tạo đơn hàng")
            }
        }
        
        composable(Screen.AddVehicle.route) {
            // Placeholder screen
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Thêm xe")
            }
        }
        
        composable(Screen.CreateService.route) {
            // Placeholder screen
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Tạo dịch vụ")
            }
        }
    }
}

data class DashboardBottomNavItem(
    val route: String,
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
