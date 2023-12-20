package xyz.larkyy.necronomicon.menu.items

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import xyz.larkyy.necronomicon.menu.MenuItem

class ReputationButton(itemStack: ItemStack, slots: ArrayList<Int>) : MenuItem(itemStack, slots) {
    override fun onClick(event: InventoryClickEvent) {
        event.whoClicked.sendMessage("Reputation button")
        event.isCancelled = true
    }
}