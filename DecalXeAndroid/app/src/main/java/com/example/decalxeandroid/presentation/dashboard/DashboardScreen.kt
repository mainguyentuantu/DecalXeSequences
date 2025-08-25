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
import com.example.decalxeandroid.presentation.customers.AddCustomerScreen
import com.example.decalxeandroid.presentation.customers.CustomerDetailScreen
import com.example.decalxeandroid.presentation.customers.CustomerEditScreen
import com.example.decalxeandroid.presentation.orders.OrdersScreen
import com.example.decalxeandroid.presentation.orders.OrderDetailScreen
import com.example.decalxeandroid.presentation.orders.OrderEditScreen
import com.example.decalxeandroid.presentation.orders.CreateOrderScreen
import com.example.decalxeandroid.presentation.vehicles.VehiclesScreen
import com.example.decalxeandroid.presentation.vehicles.AddVehicleScreen
import com.example.decalxeandroid.presentation.vehicles.VehicleDetailScreen
import com.example.decalxeandroid.presentation.vehicles.VehicleEditScreen
import com.example.decalxeandroid.presentation.services.ServicesScreen
import com.example.decalxeandroid.presentation.services.CreateServiceScreen
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
                    println("DashboardScreen: Navigation to OrderDetail requested with orderId: '$orderId'")
                    val route = Screen.OrderDetail.createRoute(orderId)
                    println("DashboardScreen: Generated route: '$route'")
                    navController.navigate(route)
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
            CustomerDetailScreen(
                customerId = customerId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToVehicle = { vehicleId ->
                    navController.navigate(Screen.VehicleDetail.createRoute(vehicleId))
                },
                onNavigateToOrder = { orderId ->
                    navController.navigate(Screen.OrderDetail.createRoute(orderId))
                },
                onNavigateToEdit = { customerIdToEdit ->
                    navController.navigate(Screen.CustomerEdit.createRoute(customerIdToEdit))
                }
            )
        }
        
        composable(
            route = Screen.OrderDetail.route,
            arguments = listOf(
                navArgument("orderId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
            println("DashboardScreen: Navigating to OrderDetail with orderId: '$orderId'")
            
            if (orderId.isEmpty()) {
                println("DashboardScreen: WARNING - orderId is empty!")
            }
            
            OrderDetailScreen(
                orderId = orderId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToCustomer = { customerId ->
                    navController.navigate(Screen.CustomerDetail.createRoute(customerId))
                },
                onNavigateToVehicle = { vehicleId ->
                    navController.navigate(Screen.VehicleDetail.createRoute(vehicleId))
                },
                onNavigateToEdit = { orderIdToEdit ->
                    navController.navigate(Screen.OrderEdit.createRoute(orderIdToEdit))
                }
            )
        }
        
        composable(
            route = Screen.VehicleDetail.route,
            arguments = listOf(
                navArgument("vehicleId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId") ?: ""
            VehicleDetailScreen(
                vehicleId = vehicleId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToCustomer = { customerId ->
                    navController.navigate(Screen.CustomerDetail.createRoute(customerId))
                },
                onNavigateToOrder = { orderId ->
                    navController.navigate(Screen.OrderDetail.createRoute(orderId))
                },
                onNavigateToEdit = { vehicleIdToEdit ->
                    navController.navigate(Screen.VehicleEdit.createRoute(vehicleIdToEdit))
                }
            )
        }
        
        composable(
            route = Screen.VehicleEdit.route,
            arguments = listOf(
                navArgument("vehicleId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId") ?: ""
            VehicleEditScreen(
                vehicleId = vehicleId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToDetail = { vehicleIdToDetail ->
                    navController.navigate(Screen.VehicleDetail.createRoute(vehicleIdToDetail)) {
                        popUpTo(Screen.VehicleDetail.createRoute(vehicleIdToDetail)) { inclusive = true }
                    }
                }
            )
        }
        
        // Create/Add screens
        composable(Screen.AddCustomer.route) {
            AddCustomerScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onCustomerCreated = { customer ->
                    // Navigate back to customers list
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = Screen.CustomerEdit.route,
            arguments = listOf(
                navArgument("customerId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val customerId = backStackEntry.arguments?.getString("customerId") ?: ""
            CustomerEditScreen(
                customerId = customerId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToDetail = { customerIdToDetail ->
                    navController.navigate(Screen.CustomerDetail.createRoute(customerIdToDetail)) {
                        popUpTo(Screen.CustomerDetail.createRoute(customerIdToDetail)) { inclusive = true }
                    }
                }
            )
        }
        
        composable(
            route = Screen.OrderEdit.route,
            arguments = listOf(
                navArgument("orderId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
            OrderEditScreen(
                orderId = orderId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToDetail = { orderIdToDetail ->
                    navController.navigate(Screen.OrderDetail.createRoute(orderIdToDetail)) {
                        popUpTo(Screen.OrderDetail.createRoute(orderIdToDetail)) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.CreateOrder.route) {
            CreateOrderScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onOrderCreated = { order ->
                    // Navigate back to orders list
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.AddVehicle.route) {
            AddVehicleScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onVehicleCreated = { vehicle ->
                    // Navigate back to vehicles list
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.CreateService.route) {
            CreateServiceScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onServiceCreated = {
                    // Navigate back to services list and refresh
                    navController.popBackStack()
                }
            )
        }
    }
}

data class DashboardBottomNavItem(
    val route: String,
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
