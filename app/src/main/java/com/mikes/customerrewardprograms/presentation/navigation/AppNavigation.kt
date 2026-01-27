package com.mikes.customerrewardprograms.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mikes.customerrewardprograms.presentation.home.HomeScreen
import com.mikes.customerrewardprograms.presentation.home.HomeViewModel
import com.mikes.customerrewardprograms.presentation.promotions.PromotionsScreen
import com.mikes.customerrewardprograms.presentation.promotions.PromotionsViewModel
import com.mikes.customerrewardprograms.presentation.rewards.RewardsScreen
import com.mikes.customerrewardprograms.presentation.rewards.RewardsViewModel
import com.mikes.customerrewardprograms.presentation.scanner.ScannerScreen
import com.mikes.customerrewardprograms.presentation.scanner.ScannerViewModel
import kotlinx.serialization.Serializable

// Type-safe navigation routes using Kotlinx Serialization
@Serializable
object Scanner

@Serializable
object Home

@Serializable
object Promotions

@Serializable
object Rewards

@Composable
fun AppNavigation(
    scannerViewModel: ScannerViewModel,
    homeViewModel: HomeViewModel,
    promotionsViewModel: PromotionsViewModel,
    rewardsViewModel: RewardsViewModel
) {
    val navController = rememberNavController()

    // TODO: Cambiar a Scanner para producción
    val startDestination = Home

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = slideInFromRight(),
        exitTransition = slideOutToLeft(),
        popEnterTransition = slideInFromLeft(),
        popExitTransition = slideOutToRight()
    ) {
        composable<Scanner> {
            ScannerScreen(
                viewModel = scannerViewModel,
                onNavigateToHome = {
                    navController.navigate(Home) {
                        popUpTo<Scanner> { inclusive = true }
                    }
                }
            )
        }

        composable<Home> {
            HomeScreen(
                viewModel = homeViewModel,
                onNavigateToLogin = {
                    navController.navigate(Scanner) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onNavigateToPromotions = {
                    navController.navigate(Promotions)
                },
                onNavigateToRewards = {
                    navController.navigate(Rewards)
                }
            )
        }

        composable<Promotions> {
            PromotionsScreen(
                viewModel = promotionsViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Rewards> {
            RewardsScreen(
                viewModel = rewardsViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}