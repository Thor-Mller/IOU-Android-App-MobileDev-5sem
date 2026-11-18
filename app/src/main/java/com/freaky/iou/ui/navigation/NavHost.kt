package com.freaky.iou.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.freaky.iou.ui.accountsettings.AccountSettingsView
import androidx.navigation.toRoute
import com.freaky.iou.ui.auth.login.LoginScreen
import com.freaky.iou.ui.auth.signup.SignupScreen
import com.freaky.iou.ui.expense.NewExpenseViewModel
import com.freaky.iou.ui.group.GroupScreen
import com.freaky.iou.ui.group.GroupViewModel
import com.freaky.iou.ui.home.HomeScreen
import com.freaky.iou.ui.newgroup.NewGroupScreen
import com.freaky.iou.ui.reauth.ReAuthScreen
import com.freaky.iou.ui.payment.viewmodels.PaymentViewModel
import com.freaky.iou.ui.payment.views.NewExpenseScreen
import com.freaky.iou.ui.payment.views.PaymentScreen
import com.freaky.iou.ui.splash.SplashScreen
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
object SplashGraph

@Serializable
object AuthGraph

@Serializable
object ReAuthGraph

@Serializable
object HomeGraph
@Serializable
object AccountGraph

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
                SplashScreen(
                    onGoToAuth = {
                    navController.navigate(AuthGraph) {
                        popUpTo(SplashGraph) { inclusive = true }
                    }
                    },
                    onGoToReAuth = {
                        navController.navigate(ReAuthGraph) {
                            popUpTo(SplashGraph) { inclusive = true }
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

        navigation<ReAuthGraph>(startDestination = ReAuthRoute) {
            composable<ReAuthRoute> {
                ReAuthScreen(
                    onLogout = {
                        navController.navigate(AuthGraph) {
                            popUpTo(ReAuthGraph) { inclusive = true }
                        }
                    },
                    onUnlocked = {
                        navController.navigate(HomeGraph) {
                            popUpTo(ReAuthGraph) { inclusive = true }
                        }
                    })
            }
        }

        navigation<HomeGraph>(startDestination = HomeRoute) {
            composable<HomeRoute> {
                HomeScreen(
                    onNavigateToCreateNewGroup = {
                        navController.navigate(NewGroupRoute) {}
                    },
                    onNavigateToAccountSettings = {
                        navController.navigate(AccountGraph) {}
                    },
                    onGoToGroup = { groupId ->
                        navController.navigate(GroupRoute(groupId.toString()))
                    })
            }
            composable<GroupRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<GroupRoute>()
                val groupId = UUID.fromString(route.groupId)

                val groupViewModel: GroupViewModel = viewModel {
                    GroupViewModel(groupId = groupId)
                }

                GroupScreen(
                    viewModel = groupViewModel,
                    onAddExpense = {
                        navController.navigate(NewExpenseRoute(groupId = route.groupId))
                    },
                    onExpenseClick = { expense, shareAmount ->
                        navController.navigate(
                            PaymentRoute(
                                expenseId = expense.id.toString(),
                                payAmount = shareAmount
                            )
                        )
                    }
                )
            }

            composable<NewExpenseRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<NewExpenseRoute>()
                val groupId = UUID.fromString(route.groupId)

                val expenseViewModel: NewExpenseViewModel = viewModel {
                    NewExpenseViewModel(initialGroupId = groupId)
                }

                NewExpenseScreen(
                    viewModel = expenseViewModel,
                    onCreate = {
                        navController.navigateUp()
                    }
                )
            }

            composable<PaymentRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<PaymentRoute>()
                val paymentViewModel: PaymentViewModel = viewModel {
                    PaymentViewModel(
                        expenseId = UUID.fromString(route.expenseId),
                        payAmount = route.payAmount
                    )
                }

                PaymentScreen(
                    viewModel = paymentViewModel,
                    onPaymentSuccess = {
                        navController.navigateUp()
                    }
                )
            }

            composable<NewGroupRoute> {
                NewGroupScreen(
                    onCancel = {
                        navController.navigateUp()
                    },
                    onGroupCreated = { groupId ->
                        navController.navigate(GroupRoute(groupId = groupId.toString())) {
                            popUpTo<HomeGraph> {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

        navigation<AccountGraph>(startDestination = AccountSettings) {
            composable<AccountSettings> {
                AccountSettingsView(goToLogin = {
                    navController.navigate(AuthGraph) {
                        popUpTo(AccountGraph) { inclusive = true }
                    }
                })
            }
        }

    }
}