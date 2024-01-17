package xyz.larkyy.necronomicon.menu.menus

import org.bukkit.entity.Player
import xyz.larkyy.necronomicon.NecroNomicon
import xyz.larkyy.necronomicon.background.Background
import xyz.larkyy.necronomicon.badge.Badge
import xyz.larkyy.necronomicon.menu.CustomMenu
import xyz.larkyy.necronomicon.menu.OthersInventorySettings
import xyz.larkyy.necronomicon.menu.items.CustomButton
import xyz.larkyy.necronomicon.profile.PlayerProfile
import xyz.larkyy.necronomicon.theme.Theme

class OthersProfileMenu(private val othersInventorySettings: OthersInventorySettings, viewingPlayer: Player, playerProfile: PlayerProfile) :
    CustomMenu(viewingPlayer, playerProfile, othersInventorySettings.size, (playerProfile.background ?: "")+ (playerProfile.theme ?: othersInventorySettings.title)) {

//    private val repCurrency = CoinsEngineAPI.getCurrency("reputation")!!

    init {
        addMenuItems(othersInventorySettings.customButtons)
        loadBadges()
        loadTheme()
        loadBackground()
        loadReputation()
    }

    private fun loadReputation() {
        val iS = othersInventorySettings.reputationButton.itemStack.clone()
        val menuItem = CustomButton(iS,othersInventorySettings.reputationButton.slots) {event ->
            event.isCancelled = true
//            val reputationBalance = CoinsEngineAPI.getBalance(viewingPlayer,repCurrency)
//            if (reputationBalance < 1) {
//                return@CustomButton
//            }
//
//            CoinsEngineAPI.removeBalance(viewingPlayer,repCurrency,1.0)
            playerProfile.reputation++

            NecroNomicon.instance!!.profileManager.saveProfile(playerProfile).thenRun {
                loadReputation()
            }
        }
        addMenuItem(menuItem,true)
    }

    private fun loadBadges() {

        for(i in 0..<othersInventorySettings.badgeSlots.size) {
            val slot = othersInventorySettings.badgeSlots[i]
            if (i >= playerProfile.badges.size) {
                break
            }
            val badgeId = playerProfile.badges[i]
            val badge = NecroNomicon.instance?.badgeHandler?.getBadge(badgeId) ?: continue
            addBadge(slot,badge)
        }
    }

    private fun addBadge(slot: Int, badge: Badge) {
        val iS = badge.item.clone()
        val menuItem = CustomButton(iS, mutableListOf(slot)) {event ->
            event.isCancelled = true
        }
        addMenuItem(menuItem,true)
    }

    private fun loadTheme() {
        val themeId = playerProfile.theme ?: return
        val theme = NecroNomicon.instance?.themeHandler?.getTheme(themeId) ?: return
        addTheme(theme)
    }

    private fun addTheme(theme: Theme) {
        val iS = theme.itemStack.clone()
        val menuItem = CustomButton(iS, mutableListOf(othersInventorySettings.themeSlot)) {event ->
            event.isCancelled = true
        }
        addMenuItem(menuItem,true)
    }

    private fun loadBackground() {
        val backgroundId = playerProfile.background ?: return
        val background = NecroNomicon.instance?.backgroundHandler?.getBackground(backgroundId) ?: return
        addBackground(background)
    }

    private fun addBackground(background: Background) {
        val iS = background.itemStack.clone()
        val menuItem = CustomButton(iS, mutableListOf(othersInventorySettings.backgroundSlot)) {event ->
            event.isCancelled = true
        }
        addMenuItem(menuItem,true)
    }
}