package com.freaky.iou.ui.data.models

import java.util.UUID

data class Expense(
    val id: UUID = UUID.randomUUID(),
    val user: User = User(),
    val name: String = "",
    val amount: Float = 0F,
    val payed: Float = 0F,
    val group: Group = Group(),
)