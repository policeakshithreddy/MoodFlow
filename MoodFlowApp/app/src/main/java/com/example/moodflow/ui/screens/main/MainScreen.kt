package com.example.moodflow.ui.screens.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moodflow.navigation.Route
import com.example.moodflow.ui.components.BottomNavBar
import com.example.moodflow.ui.components.NavItem
import com.example.moodflow.ui.screens.home.HomeScreen
import com.example.moodflow.ui.screens.journey.JourneyScreen
import com.example.moodflow.ui.screens.observations.ObservationsScreen

@Composable
fun MainScreen(
    rootNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Route.HOME

    val currentNavItem = when (currentRoute) {
        Route.HOME -> NavItem.HOME
        Route.JOURNEY -> NavItem.JOURNEY
        Route.OBSERVATIONS -> NavItem.INSIGHTS
        Route.YOU -> NavItem.YOU
        else -> NavItem.HOME
    }

    Box(modifier = modifier.fillMaxSize()) {
        NavHost(
            navController = bottomNavController,
            startDestination = Route.HOME,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(
                route = Route.HOME,
                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)) },
                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(500)) }
            ) {
                HomeScreen(
                    onNavigateToLog = { rootNavController.navigate(Route.MOOD_LOGGING) },
                    onNavigateTo = { navItem ->
                        val routeStr = when (navItem) {
                            NavItem.HOME -> Route.HOME
                            NavItem.JOURNEY -> Route.JOURNEY
                            NavItem.INSIGHTS -> Route.OBSERVATIONS
                            NavItem.YOU -> Route.YOU
                        }
                        if (routeStr != Route.HOME) {
                            bottomNavController.navigate(routeStr) {
                                popUpTo(Route.HOME) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }

            composable(
                route = Route.JOURNEY,
                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(500)) },
                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)) }
            ) {
                JourneyScreen(
                    onNavigate = { },
                    onMoodClick = { rootNavController.navigate(Route.MOOD_DETAILS) }
                )
            }

            composable(
                route = Route.OBSERVATIONS,
                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(500)) },
                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)) }
            ) {
                com.example.moodflow.ui.screens.insights.InsightsScreen(
                    onNavigateTo = { navItem ->
                        val routeStr = when (navItem) {
                            NavItem.HOME -> Route.HOME
                            NavItem.JOURNEY -> Route.JOURNEY
                            NavItem.INSIGHTS -> Route.OBSERVATIONS
                            NavItem.YOU -> Route.YOU
                        }
                        if (routeStr != Route.OBSERVATIONS) {
                            bottomNavController.navigate(routeStr) {
                                popUpTo(Route.HOME) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }

            composable(
                route = Route.YOU,
                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(500)) },
                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)) }
            ) {
                com.example.moodflow.ui.screens.profile.ProfileScreen(
                    onNavigateTo = { navItem ->
                        val routeStr = when (navItem) {
                            NavItem.HOME -> Route.HOME
                            NavItem.JOURNEY -> Route.JOURNEY
                            NavItem.INSIGHTS -> Route.OBSERVATIONS
                            NavItem.YOU -> Route.YOU
                        }
                        if (routeStr != Route.YOU) {
                            bottomNavController.navigate(routeStr) {
                                popUpTo(Route.HOME) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
        
        BottomNavBar(
            currentRoute = currentNavItem,
            onNavigate = { navItem ->
                val targetRoute = when (navItem) {
                    NavItem.HOME -> Route.HOME
                    NavItem.JOURNEY -> Route.JOURNEY
                    NavItem.INSIGHTS -> Route.OBSERVATIONS
                    NavItem.YOU -> Route.YOU
                }
                if (targetRoute != currentRoute) {
                    bottomNavController.navigate(targetRoute) {
                        popUpTo(Route.HOME) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            modifier = androidx.compose.ui.Alignment.BottomCenter.let { align -> 
                Modifier.align(align)
            }
        )
    }
}
