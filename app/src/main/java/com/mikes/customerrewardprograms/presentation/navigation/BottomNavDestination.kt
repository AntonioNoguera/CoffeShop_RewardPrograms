package com.mikes.customerrewardprograms.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavDestination(
    val route: Any,
    val icon: ImageVector,
    val label: String,
    val requiresMaster: Boolean = false
) {
    data object HomeTab : BottomNavDestination(
        route = Home,
        icon = Icons.Default.Home,
        label = "Inicio"
    )

    data object PromotionsTab : BottomNavDestination(
        route = Promotions,
        icon = Icons.Default.LocalOffer,
        label = "Promociones"
    )

    data object RewardsTab : BottomNavDestination(
        route = Rewards,
        icon = Icons.Default.Add,
        label = "Agregar",
        requiresMaster = true
    )

    companion object {
        fun all() = listOf(HomeTab, PromotionsTab, RewardsTab)
        fun forUser(isMaster: Boolean) = all().filter { !it.requiresMaster || isMaster }
    }
}
