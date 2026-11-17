package com.freaky.iou.ui.groupsettings.models

import java.util.UUID

data class GroupSettings constructor(
    val name: String,
    val members: List<UUID>,
){
}