package com.freaky.iou.ui.data.models

import java.util.UUID

data class Group(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val creator: User,
)