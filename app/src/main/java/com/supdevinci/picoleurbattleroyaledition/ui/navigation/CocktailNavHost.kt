package com.supdevinci.picoleurbattleroyaledition.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.supdevinci.picoleurbattleroyaledition.ui.AccountScreen
import com.supdevinci.picoleurbattleroyaledition.ui.DetailCocktailScreen
import com.supdevinci.picoleurbattleroyaledition.ui.FavoriteCocktailScreen
import com.supdevinci.picoleurbattleroyaledition.ui.LeaderboardScreen
import com.supdevinci.picoleurbattleroyaledition.ui.SearchCocktailScreen
import com.supdevinci.picoleurbattleroyaledition.ui.SplashScreen
import com.supdevinci.picoleurbattleroyaledition.ui.TrendingScreen

object Routes {
    const val SPLASH = "splash"
    const val LEADERBOARD = "leaderboard"
    const val SEARCH = "search"
    const val TRENDING = "trending"
    const val DETAIL = "detail"
    const val FAVORITES = "favorites"
    const val ACCOUNT = "account"
}

@Composable
fun CocktailNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH,
        modifier = modifier
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }

        composable(Routes.LEADERBOARD) {
            LeaderboardScreen()
        }

        composable(Routes.SEARCH) {
            SearchCocktailScreen(
                onCocktailSelected = { id -> navController.navigate("${Routes.DETAIL}/$id") },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.TRENDING) {
            TrendingScreen(
                onCocktailSelected = { id -> navController.navigate("${Routes.DETAIL}/$id") }
            )
        }

        composable(Routes.FAVORITES) {
            FavoriteCocktailScreen(
                onCocktailSelected = { id -> navController.navigate("${Routes.DETAIL}/$id") },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.ACCOUNT) {
            AccountScreen()
        }

        composable(
            route = "${Routes.DETAIL}/{cocktailId}",
            arguments = listOf(navArgument("cocktailId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("cocktailId") ?: ""
            DetailCocktailScreen(
                cocktailId = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
