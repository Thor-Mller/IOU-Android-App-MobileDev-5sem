package com.freaky.iou.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.freaky.iou.ui.auth.UserViewModel
import com.freaky.iou.ui.auth.login.LoginScreen
import com.freaky.iou.ui.auth.signup.SignupScreen
import com.freaky.iou.ui.group.viewmodels.NewGroupViewModel
import com.freaky.iou.ui.group.views.NewGroupScreen
import com.freaky.iou.ui.splash.SplashScreen
import kotlinx.serialization.Serializable

@Serializable
object SplashGraph
@Serializable
object AuthGraph

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val userViewModel: UserViewModel = viewModel()

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
                }, onLoginSuccess = { username ->
                    userViewModel.onUserAuthenticated(username)
                })
            }
            composable<SignupRoute> {
                SignupScreen(onNavigateToLogin = {
                    navController.navigateUp()
                }, onSignupSuccess = { username ->
                    userViewModel.onUserAuthenticated(username)
                    navController.navigate(LoginRoute)
                })
            }
        }

        composable<NewGroupRoute> {
            val newGroupViewModel: NewGroupViewModel = viewModel()
            NewGroupScreen(
                viewModel = newGroupViewModel,
                onCancel = { navController.navigateUp() },
                onCreate = { group ->
                    // TODO: save the group and send invites once the backend has a Group table
                    navController.navigateUp()
                }
            )
        }

    }
}