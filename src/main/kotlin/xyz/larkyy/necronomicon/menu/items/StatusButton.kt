package xyz.larkyy.necronomicon.menu.items

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import xyz.larkyy.necronomicon.menu.MenuItem

class StatusButton(itemStack: ItemStack, slots: ArrayList<Int>) : MenuItem(itemStack, slots) {
    override fun onClick(event: InventoryClickEvent) {
        event.whoClicked.sendMessage("Change status")
        event.isCancelled = true
    }
}