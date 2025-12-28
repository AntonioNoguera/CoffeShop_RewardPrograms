package com.mikes.customerrewardprograms.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

/**
 * Duración estándar de las animaciones
 */
object AnimationConstants {
    const val DURATION_ENTER = 300
    const val DURATION_EXIT = 300
    const val DURATION_POP_ENTER = 300
    const val DURATION_POP_EXIT = 300
}

/**
 * Transición de deslizamiento de derecha a izquierda (clásica de Android)
 */
fun slideInFromRight(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition {
    return {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = tween(AnimationConstants.DURATION_ENTER)
        ) + fadeIn(animationSpec = tween(AnimationConstants.DURATION_ENTER))
    }
}

fun slideOutToLeft(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition {
    return {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = tween(AnimationConstants.DURATION_EXIT)
        ) + fadeOut(animationSpec = tween(AnimationConstants.DURATION_EXIT))
    }
}

fun slideInFromLeft(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition {
    return {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Right,
            animationSpec = tween(AnimationConstants.DURATION_POP_ENTER)
        ) + fadeIn(animationSpec = tween(AnimationConstants.DURATION_POP_ENTER))
    }
}

fun slideOutToRight(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition {
    return {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Right,
            animationSpec = tween(AnimationConstants.DURATION_POP_EXIT)
        ) + fadeOut(animationSpec = tween(AnimationConstants.DURATION_POP_EXIT))
    }
}

/**
 * Transición de fade (sin deslizamiento)
 */
fun fadeInTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition {
    return {
        fadeIn(animationSpec = tween(AnimationConstants.DURATION_ENTER))
    }
}

fun fadeOutTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition {
    return {
        fadeOut(animationSpec = tween(AnimationConstants.DURATION_EXIT))
    }
}

/**
 * Transición de escala (zoom in/out)
 */
fun scaleInTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition {
    return {
        scaleIn(
            initialScale = 0.9f,
            animationSpec = tween(AnimationConstants.DURATION_ENTER)
        ) + fadeIn(animationSpec = tween(AnimationConstants.DURATION_ENTER))
    }
}

fun scaleOutTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition {
    return {
        scaleOut(
            targetScale = 0.9f,
            animationSpec = tween(AnimationConstants.DURATION_EXIT)
        ) + fadeOut(animationSpec = tween(AnimationConstants.DURATION_EXIT))
    }
}

/**
 * Transición vertical (de abajo hacia arriba) - típica para modales
 */
fun slideInFromBottom(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition {
    return {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Up,
            animationSpec = tween(AnimationConstants.DURATION_ENTER)
        ) + fadeIn(animationSpec = tween(AnimationConstants.DURATION_ENTER))
    }
}

fun slideOutToBottom(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition {
    return {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Down,
            animationSpec = tween(AnimationConstants.DURATION_POP_EXIT)
        ) + fadeOut(animationSpec = tween(AnimationConstants.DURATION_POP_EXIT))
    }
}