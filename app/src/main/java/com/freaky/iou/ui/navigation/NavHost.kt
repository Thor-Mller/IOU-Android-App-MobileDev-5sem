package com.freaky.iou.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.freaky.iou.ui.auth.login.LoginScreen
import com.freaky.iou.ui.auth.signup.SignupScreen
import com.freaky.iou.ui.home.HomeScreen
import com.freaky.iou.ui.newgroup.NewGroupScreen
import com.freaky.iou.ui.splash.SplashScreen
import kotlinx.serialization.Serializable

@Serializable
object SplashGraph

@Serializable
object AuthGraph

@Serializable
object HomeGraph

@Serializable
object NewGroupGraph

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SplashGraph,

        //region TRANSITIONS

        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, tween(300)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, tween(300)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, tween(300)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, tween(300)
            )
        },

        //endregion TRANSITIONS

    ) {
        navigation<SplashGraph>(startDestination = SplashRoute) {
            composable<SplashRoute> {
                SplashScreen(onGoToApp = {
                    navController.navigate(AuthGraph) {
                        popUpTo(SplashRoute) { inclusive = true }
                    }
                })
            }
        }

        navigation<AuthGraph>(startDestination = LoginRoute) {
            composable<LoginRoute> {
                LoginScreen(onNavigateToSignup = {
                    navController.navigate(SignupRoute)
                }, onLoginSuccess = {
                    navController.navigate(HomeGraph) {
                        popUpTo(AuthGraph) { inclusive = true }
                    }
                })
            }
            composable<SignupRoute> {
                SignupScreen(onNavigateToLogin = {
                    navController.navigateUp()
                }, onSignupSuccess = {
                    navController.navigate(HomeGraph) {
                        popUpTo(AuthGraph) { inclusive = true }
                    }
                })
            }
        }

        navigation<HomeGraph>(startDestination = HomeRoute) {
            composable<HomeRoute> {
                HomeScreen(
                    onNavigateToCreateNewGroup = {
                        navController.navigate(NewGroupGraph) {}
                    })
            }
        }

        navigation<NewGroupGraph>(startDestination = NewGroupRoute) {
            composable<NewGroupRoute> {
                NewGroupScreen(onCancel = {
                    navController.navigate(HomeGraph) {
                        popUpTo(NewGroupRoute) { inclusive = true }
                    }
                }, onGroupCreated = {})
            }
        }

    }
}