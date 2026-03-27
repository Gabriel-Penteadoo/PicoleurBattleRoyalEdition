package com.supdevinci.picoleurbattleroyaledition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.supdevinci.picoleurbattleroyaledition.ui.navigation.CocktailNavHost
import com.supdevinci.picoleurbattleroyaledition.ui.navigation.Routes
import com.supdevinci.picoleurbattleroyaledition.ui.theme.PicoleurBattleRoyalEditionTheme

private data class BottomNavItem(val label: String, val route: String, val icon: ImageVector)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PicoleurBattleRoyalEditionTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val bottomNavItems = listOf(
                    BottomNavItem("Classement", Routes.LEADERBOARD, Icons.Default.Home),
                    BottomNavItem("Recherche", Routes.SEARCH, Icons.Default.Search),
                    BottomNavItem("Tendances", Routes.TRENDING, Icons.Default.Star),
                    BottomNavItem("Favoris", Routes.FAVORITES, Icons.Default.Favorite),
                    BottomNavItem("Compte", Routes.ACCOUNT, Icons.Default.Person)
                )

                // La BottomBar est masquée sur l'écran de détail et le splash
                val showBottomBar = currentRoute != null
                    && currentRoute != Routes.SPLASH
                    && !currentRoute.startsWith(Routes.DETAIL)

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar {
                                bottomNavItems.forEach { item ->
                                    NavigationBarItem(
                                        selected = currentRoute == item.route,
                                        onClick = {
                                            navController.navigate(item.route) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = item.icon,
                                                contentDescription = item.label
                                            )
                                        },
                                        label = { Text(item.label) }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    CocktailNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
