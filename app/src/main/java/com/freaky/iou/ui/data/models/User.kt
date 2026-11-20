package com.freaky.iou.ui.data.models

data class User(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val phone: String? = null,
    val password: String = "",
    val totalMissing: Int = 0,
    val totalDebt: Int = 0,
)