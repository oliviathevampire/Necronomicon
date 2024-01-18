package xyz.larkyy.necronomicon.menu

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent

class MenuListener : Listener {

    @EventHandler
    fun onInvClick(event: InventoryClickEvent) {
        val menu = event.view.topInventory
        val holder = menu.holder ?: return

        if (holder !is CustomMenu) return
        if (event.action == InventoryAction.COLLECT_TO_CURSOR || event.action == InventoryAction.PLACE_SOME) {
            event.isCancelled = true
            return
        }
        if (event.clickedInventory == menu) holder.onClick(event)
    }

    @EventHandler
    fun onDrag(event: InventoryDragEvent) {
        val menu = event.inventory
        val holder = menu.holder ?: return

        if (holder !is CustomMenu) return
        event.isCancelled = true
    }

}