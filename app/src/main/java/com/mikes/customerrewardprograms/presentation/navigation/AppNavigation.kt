package com.mikes.customerrewardprograms.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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

    // Observar estado del usuario para determinar si es master
    val homeState by homeViewModel.state.collectAsState()
    val isMaster = homeState.user?.isMaster == true

    // Determinar si mostrar bottom bar según la ruta actual
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    // Scanner NO muestra bottom bar (es flujo de login)
    val showBottomBar = currentRoute?.hasRoute(Scanner::class) == false

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                AppBottomNavigationBar(
                    navController = navController,
                    isMaster = isMaster
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding),
            // Transiciones por defecto: fade para tabs
            enterTransition = fadeInTransition(),
            exitTransition = fadeOutTransition(),
            popEnterTransition = fadeInTransition(),
            popExitTransition = fadeOutTransition()
        ) {
            // Scanner con transiciones especiales (slide)
            composable<Scanner>(
                enterTransition = slideInFromRight(),
                exitTransition = slideOutToLeft(),
                popEnterTransition = slideInFromLeft(),
                popExitTransition = slideOutToRight()
            ) {
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
                    }
                )
            }

            composable<Promotions> {
                PromotionsScreen(
                    viewModel = promotionsViewModel
                )
            }

            composable<Rewards> {
                RewardsScreen(
                    viewModel = rewardsViewModel
                )
            }
        }
    }
}
