package com.example.decalxeandroid.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Dashboard : Screen("dashboard")
    object Customers : Screen("customers")
    object Orders : Screen("orders")
    object Vehicles : Screen("vehicles")
    object Services : Screen("services")
    object Profile : Screen("profile")
    object ApiDebug : Screen("api_debug")
    object OrderDetail : Screen("order_detail/{orderId}") {
        fun createRoute(orderId: String) = "order_detail/$orderId"
    }
    object VehicleDetail : Screen("vehicle_detail/{vehicleId}") {
        fun createRoute(vehicleId: String) = "vehicle_detail/$vehicleId"
    }
    object CustomerDetail : Screen("customer_detail/{customerId}") {
        fun createRoute(customerId: String) = "customer_detail/$customerId"
    }
    object CreateOrder : Screen("create_order")
    object AddVehicle : Screen("add_vehicle")
    object AddCustomer : Screen("add_customer")
    object CreateService : Screen("create_service")
}
