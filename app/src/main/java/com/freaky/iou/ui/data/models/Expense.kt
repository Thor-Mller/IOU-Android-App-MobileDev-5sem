package com.freaky.iou.ui.data.models

import java.util.UUID

data class Expense(
    val id: UUID = UUID.randomUUID(),
    val user: User = User(),
    val name: String = "",
    val amount: Int = 0,
    val payed: Int = 0,
    val group: Group = Group(),
)