package com.example.moodflow.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moodflow.ui.screens.home.HomeScreen
import com.example.moodflow.ui.screens.journey.JourneyScreen
import com.example.moodflow.ui.screens.mooddetails.MoodDetailsScreen
import com.example.moodflow.ui.screens.moodlogging.MoodLoggingScreen
import com.example.moodflow.ui.screens.observations.ObservationsScreen
import com.example.moodflow.ui.screens.onboarding.OnboardingScreen
import com.example.moodflow.ui.components.NavItem
import com.example.moodflow.data.MoodEntry

@Composable
fun MoodFlowNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Route.ONBOARDING
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Route.ONBOARDING) {
            OnboardingScreen(
                onGetStarted = {
                    navController.navigate(Route.MAIN) {
                        popUpTo(Route.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = Route.MAIN,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(500)) }
        ) {
            com.example.moodflow.ui.screens.main.MainScreen(rootNavController = navController)
        }
        composable(
            route = Route.MOOD_LOGGING,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, animationSpec = tween(500)) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, animationSpec = tween(500)) }
        ) {
            MoodLoggingScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Route.MOOD_DETAILS,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(500)) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)) }
        ) {
            val dummyEntry = MoodEntry(
                moodIndex = 1,
                energyLevel = 2,
                contexts = "Work, Relaxed",
                reflection = "Felt calm today.",
                timestamp = System.currentTimeMillis()
            )
            MoodDetailsScreen(
                moodEntry = dummyEntry,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
