package xyz.larkyy.necronomicon.menu

import xyz.larkyy.necronomicon.menu.items.ReputationButton

data class OthersInventorySettings(
    val title: String,
    val size: Int,
    val reputationButton: ReputationButton,
    val themeSlot: Int,
    val backgroundSlot: Int,
    val badgeSlots: ArrayList<Int>,
    val customButtons: ArrayList<MenuItem>
) {

}