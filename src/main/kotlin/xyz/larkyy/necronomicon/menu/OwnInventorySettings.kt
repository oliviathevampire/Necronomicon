package xyz.larkyy.necronomicon.menu

import xyz.larkyy.necronomicon.menu.items.StatusButton

data class OwnInventorySettings(
    val title: String,
    val size: Int,
    val statusButton: StatusButton,
    val themeSlot: Int,
    val backgroundSlot: Int,
    val badgeSlots: MutableList<Int>,
    val customButtons: MutableList<MenuItem>
) {



}