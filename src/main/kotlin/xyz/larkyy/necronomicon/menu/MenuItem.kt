package xyz.larkyy.necronomicon.menu

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import xyz.larkyy.necronomicon.profile.PlayerProfile

abstract class MenuItem(
    val itemStack: ItemStack,
    val slots: MutableList<Int>
) {

//    private val currency = CoinsEngineAPI.getCurrency("reputation")!!
    abstract fun onClick(event: InventoryClickEvent)

    fun getFormattedItem(playerProfile: PlayerProfile, viewingPlayer: Player): ItemStack {
        val iS = itemStack.clone()
        if (iS.type==Material.AIR) return iS
        val im = iS.itemMeta?: return iS

        val displayName = im.displayName()
        if (displayName != null) {
            im.displayName(replacePlaceholders(displayName,viewingPlayer, playerProfile))
        }

        val lore = im.lore()
        if (lore != null) {
            val newLore = ArrayList<Component>()
            lore.forEach { line -> newLore.add(replacePlaceholders(line,viewingPlayer, playerProfile)) }
            im.lore(newLore)
        }

        iS.itemMeta = im
        return iS
    }

    private fun replacePlaceholders(value: Component, viewingPlayer: Player, playerProfile: PlayerProfile): Component {
        return value.replaceText {
            it.match("%player%").replacement(playerProfile.userName)
        }.replaceText {
            it.match("%reputation%").replacement(playerProfile.reputation.toString())
        }/*.replaceText {
            it.match("%available-reputation%").replacement((CoinsEngineAPI.getBalance(viewingPlayer,currency).toInt()).toString())
        }*/.replaceText {
            it.match("%theme%").replacement(playerProfile.theme?:"")
        }.replaceText {
            it.match("%background%").replacement(playerProfile.background?:"")
        }.replaceText {
            it.match("%status%").replacement(playerProfile.status?:"")
        }
    }

}