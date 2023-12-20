package xyz.larkyy.necronomicon.menu

import net.kyori.adventure.text.Component
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import xyz.larkyy.necronomicon.profile.PlayerProfile

abstract class MenuItem(
    val itemStack: ItemStack,
    val slots: ArrayList<Int>
) {
    abstract fun onClick(event: InventoryClickEvent)

    fun getFormattedItem(playerProfile: PlayerProfile): ItemStack {
        val iS = itemStack.clone()
        val im = iS.itemMeta?: return iS

        val displayName = im.displayName()
        if (displayName != null) {
            im.displayName(replacePlaceholders(displayName,playerProfile))
        }

        val lore = im.lore()
        if (lore != null) {
            val newLore = ArrayList<Component>()
            lore.forEach { line ->
                newLore.add(replacePlaceholders(line,playerProfile))
            }
            im.lore(newLore)
        }

        iS.itemMeta = im
        return iS
    }

    private fun replacePlaceholders(value: Component, playerProfile: PlayerProfile): Component {
        return value.replaceText {
            it.match("%player%").replacement(playerProfile.userName)
        }.replaceText {
            it.match("%reputation%").replacement(playerProfile.reputation.toString())
        }.replaceText {
            it.match("%theme%").replacement(playerProfile.theme?:"")
        }.replaceText {
            it.match("%background%").replacement(playerProfile.background?:"")
        }.replaceText {
            it.match("%status%").replacement(playerProfile.status?:"")
        }
    }

}