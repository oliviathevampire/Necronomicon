package xyz.larkyy.necronomicon.background

import org.bukkit.inventory.ItemStack

data class Background(
    val id: String,
    val itemStack: ItemStack,
    val value: String,
    val length: Int
)
