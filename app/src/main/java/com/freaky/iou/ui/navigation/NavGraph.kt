package com.freaky.iou.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object SplashRoute

@Serializable
object LoginRoute

@Serializable
object SignupRoute

@Serializable
object ReAuthRoute

@Serializable
object HomeRoute

@Serializable
data class GroupRoute(val groupId: String)

@Serializable
data class NewExpenseRoute(val groupId: String)

@Serializable
data class PaymentRoute(
    val expenseId: String,
    val payAmount: Int
)
@Serializable
data object NewGroupRoute

@Serializable
object AccountSettings