package com.freaky.iou.ui.data.models

data class User(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val totalMissing: Double = 0.0,
    val totalDebt: Double = 0.0,
)