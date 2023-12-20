package xyz.larkyy.necronomicon.menu.items

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import xyz.larkyy.necronomicon.menu.MenuItem
import java.util.function.Consumer

class CustomButton(itemStack: ItemStack, slots: ArrayList<Int>, val onClick: Consumer<InventoryClickEvent>) : MenuItem(itemStack, slots) {


    override fun onClick(event: InventoryClickEvent) {
        onClick.accept(event)
    }
}