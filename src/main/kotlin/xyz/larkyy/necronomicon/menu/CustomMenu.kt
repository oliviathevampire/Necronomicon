package xyz.larkyy.necronomicon.menu

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import xyz.larkyy.necronomicon.profile.PlayerProfile

abstract class CustomMenu(val viewingPlayer: Player, val playerProfile: PlayerProfile, size: Int, title: String): InventoryHolder {

    private val inventory: Inventory
    private val menuItems: HashMap<Int,MenuItem> = HashMap()

    init {
        inventory = Bukkit.createInventory(this,size,MiniMessage.miniMessage().deserialize(title))
    }

    override fun getInventory(): Inventory {
        return inventory
    }

    fun onClick(event: InventoryClickEvent) {
        menuItems[event.slot]?.onClick(event)
    }

    fun addMenuItem(menuItem: MenuItem, setItem: Boolean = true) {
        val iS = menuItem.getFormattedItem(playerProfile)
        for (slot in menuItem.slots) {
            menuItems[slot] = menuItem
            if (setItem) inventory.setItem(slot,iS)
        }
    }

    fun addMenuItems(menuItems: ArrayList<MenuItem>) {
        for (menuItem in menuItems) {
            addMenuItem(menuItem)
        }
    }
}