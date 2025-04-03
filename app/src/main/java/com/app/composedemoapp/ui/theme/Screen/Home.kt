package com.app.composedemoapp.ui.theme.Screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Define Bottom Navigation Items
sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object Profile : BottomNavItem("profile", "Profile", Icons.Default.Person)
    object Login : BottomNavItem("Login", "Login", Icons.Default.Person)
    object Recipes : BottomNavItem("Recipes", "Recipes", Icons.Default.Person)
    object Dialpad : BottomNavItem("Dialpad", "Dialpad", Icons.Default.Person)

}

// Material 3 Bottom Navigation Bar
@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(BottomNavItem.Home,
        BottomNavItem.Profile,
        BottomNavItem.Recipes,
        BottomNavItem.Login,
        BottomNavItem.Dialpad,
    )
    NavigationBar {
        val currentRoute = navController.currentDestination?.route
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) }
            )
        }
    }
}


// Profile Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Profile") }) }
    ) { padding ->
        Text(text = "Welcome to Profile Screen", modifier = Modifier.padding(padding))
    }
}
//@Preview(showBackground = true)
@Composable
fun Prev() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        NavHost(navController, startDestination = BottomNavItem.Home.route, Modifier.padding(paddingValues)) {
            composable(BottomNavItem.Home.route) { DashboardScreen() }
            composable(BottomNavItem.Profile.route) { DashboardScreen() }
            composable(BottomNavItem.Login.route) { ProfileScreen() }
            composable(BottomNavItem.Dialpad.route) { ProfileScreen() }
            composable(BottomNavItem.Recipes.route) { ProfileScreen() }
        }
    }
}
// Main Screen with Navigation
@Composable
fun MainScreen(navController: NavController) {
    Prev()
}

