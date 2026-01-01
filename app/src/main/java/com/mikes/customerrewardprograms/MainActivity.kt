package com.mikes.customerrewardprograms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mikes.customerrewardprograms.data.repository.PromotionRepository
import com.mikes.customerrewardprograms.data.repository.UserRepository
import com.mikes.customerrewardprograms.presentation.home.HomeViewModel
import com.mikes.customerrewardprograms.presentation.navigation.AppNavigation
import com.mikes.customerrewardprograms.presentation.promotions.PromotionsViewModel
import com.mikes.customerrewardprograms.presentation.rewards.RewardsViewModel
import com.mikes.customerrewardprograms.presentation.scanner.ScannerViewModel
import com.mikes.customerrewardprograms.ui.theme.CustomerRewardProgramsTheme


// In a real app, use Koin, Hilt, or Dagger for dependency injection
private val userRepository = UserRepository()
private val promotionRepository = PromotionRepository()

private val scannerViewModel by lazy { ScannerViewModel(userRepository) }
private val homeViewModel by lazy { HomeViewModel(userRepository) }
private val promotionsViewModel by lazy { PromotionsViewModel(promotionRepository) }
private val rewardsViewModel by lazy { RewardsViewModel(userRepository) }


class MainActivity : ComponentActivity() {

    // In a real app, use Koin, Hilt, or Dagger for dependency injection
    private val userRepository = UserRepository()
    private val promotionRepository = PromotionRepository()

    private val scannerViewModel by lazy { ScannerViewModel(userRepository) }
    private val homeViewModel by lazy { HomeViewModel(userRepository) }
    private val promotionsViewModel by lazy { PromotionsViewModel(promotionRepository) }
    private val rewardsViewModel by lazy { RewardsViewModel(userRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        var keepSplash = true
        splashScreen.setKeepOnScreenCondition { keepSplash }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        Thread {
            Thread.sleep(2000)
            keepSplash = false
        }.start()

        setContent {
            CustomerRewardProgramsTheme {
                AppNavigation(
                    scannerViewModel = scannerViewModel,
                    homeViewModel = homeViewModel,
                    promotionsViewModel = promotionsViewModel,
                    rewardsViewModel = rewardsViewModel
                )
            }
        }
    }
}

