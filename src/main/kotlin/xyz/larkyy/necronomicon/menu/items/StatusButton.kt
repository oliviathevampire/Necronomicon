package xyz.larkyy.necronomicon.menu.items

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.wesjd.anvilgui.AnvilGUI
import net.wesjd.anvilgui.AnvilGUI.ResponseAction
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import xyz.larkyy.necronomicon.NecroNomicon
import xyz.larkyy.necronomicon.menu.MenuItem
import xyz.larkyy.necronomicon.profile.PlayerProfile

class StatusButton(itemStack: ItemStack, slots: MutableList<Int>) : MenuItem(itemStack, slots) {
    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
    }

    fun openStatusEditor(player: Player, profile: PlayerProfile, runnable: Runnable) {
        AnvilGUI.Builder()
            .plugin(NecroNomicon.instance!!)
            .title("Your status:")
            .itemLeft(ItemStack(Material.PAPER).apply {
                val im = this.itemMeta!!
                im.displayName(MiniMessage.miniMessage().deserialize("<white>"))
                this.itemMeta = im
            })
            .onClick {int, state ->
                if (int == 2) {
                    profile.status = state.text
                    NecroNomicon.instance?.profileManager?.saveProfile(profile)
                    runnable.run()
                    return@onClick listOf(ResponseAction.close())
                }
                return@onClick listOf()
            }
            .open(player)
    }
}