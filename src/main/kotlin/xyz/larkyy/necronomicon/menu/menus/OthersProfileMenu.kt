package xyz.larkyy.necronomicon.menu.menus

import org.bukkit.entity.Player
import xyz.larkyy.necronomicon.menu.CustomMenu
import xyz.larkyy.necronomicon.menu.OthersInventorySettings
import xyz.larkyy.necronomicon.profile.PlayerProfile

class OthersProfileMenu(othersInventorySettings: OthersInventorySettings, viewingPlayer: Player, playerProfile: PlayerProfile) :
    CustomMenu(viewingPlayer, playerProfile, othersInventorySettings.size, othersInventorySettings.title) {

    init {
        othersInventorySettings.customButtons
    }
}