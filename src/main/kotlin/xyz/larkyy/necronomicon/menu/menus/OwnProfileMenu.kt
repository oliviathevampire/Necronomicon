package xyz.larkyy.necronomicon.menu.menus

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.larkyy.necronomicon.NecroNomicon
import xyz.larkyy.necronomicon.background.Background
import xyz.larkyy.necronomicon.badge.Badge
import xyz.larkyy.necronomicon.menu.CustomMenu
import xyz.larkyy.necronomicon.menu.OwnInventorySettings
import xyz.larkyy.necronomicon.menu.items.CustomButton
import xyz.larkyy.necronomicon.profile.PlayerProfile
import xyz.larkyy.necronomicon.theme.Theme

class OwnProfileMenu(private val ownInventorySettings: OwnInventorySettings, viewingPlayer: Player, playerProfile: PlayerProfile) :
    CustomMenu(viewingPlayer, playerProfile, ownInventorySettings.size, ownInventorySettings.title) {

    init {
        addMenuItems(ownInventorySettings.customButtons)
        loadStatusButton()
        loadBadges()
        loadTheme()
        loadBackground()
    }

    private fun loadStatusButton() {
        addMenuItem(ownInventorySettings.statusButton)
    }

    private fun loadBadges() {

        for(i in 0..<ownInventorySettings.badgeSlots.size) {
            val slot = ownInventorySettings.badgeSlots[i]
            if (i >= playerProfile.badges.size) {
                addEmptyBadge(slot,true)
                continue
            }
            val badgeId = playerProfile.badges[i]
            val badge = NecroNomicon.instance?.badgeHandler?.getBadge(badgeId)
            if (badge == null) {
                addEmptyBadge(slot,true)
                continue
            }

            addFilledBadge(slot,badge,true)
        }

        for (slot in ownInventorySettings.badgeSlots) {
            playerProfile.badges
        }
    }

    private fun addFilledBadge(slot: Int, badge: Badge, setItem: Boolean) {
        val iS = badge.item.clone()

        val menuItem = CustomButton(iS, ArrayList(slot)) { event ->
            viewingPlayer.sendMessage("You have taken the badge!")
            addEmptyBadge(slot, false)
        }
        addMenuItem(menuItem,setItem)
    }

    private fun addEmptyBadge(slot: Int, setItem: Boolean) {
        addMenuItem(CustomButton(ItemStack(Material.AIR), ArrayList(slot)) { event ->
            val badge = NecroNomicon.instance?.badgeHandler?.getBadge(event.cursor)

            if (badge == null) {
                event.isCancelled = true
                viewingPlayer.sendMessage("This item is not a badge!")
                return@CustomButton
            }

            viewingPlayer.sendMessage("You have added a badge")
            addFilledBadge(slot, badge, false)
        },setItem)
    }

    private fun loadTheme() {
        val themeId = playerProfile.theme
        if (themeId != null) {
            val theme = NecroNomicon.instance?.themeHandler?.getTheme(themeId)

            if (theme != null) {
                addFilledTheme(theme,true)
                return
            }
        }
        addEmptyTheme(true)
    }

    private fun addFilledTheme(theme: Theme, setItem: Boolean) {
        val iS = theme.itemStack.clone()

        val menuItem = CustomButton(iS, ArrayList(ownInventorySettings.themeSlot)) { event ->
            viewingPlayer.sendMessage("You have taken the theme!")
            addEmptyTheme(false)
        }
        addMenuItem(menuItem,setItem)
    }

    private fun addEmptyTheme(setItem: Boolean) {
        addMenuItem(CustomButton(ItemStack(Material.AIR), ArrayList(ownInventorySettings.themeSlot)) { event ->
            val theme = NecroNomicon.instance?.themeHandler?.getTheme(event.cursor)

            if (theme == null) {
                event.isCancelled = true
                viewingPlayer.sendMessage("This item is not a theme!")
                return@CustomButton
            }

            viewingPlayer.sendMessage("You have added a theme")
            addFilledTheme(theme, false)
        },setItem)
    }

    private fun loadBackground() {
        val backgroundId = playerProfile.theme
        if (backgroundId != null) {
            val background = NecroNomicon.instance?.backgroundHandler?.getBackground(backgroundId)

            if (background != null) {
                addFilledBackground(background,true)
                return
            }
        }
        addEmptyBackground(true)
    }

    private fun addFilledBackground(background: Background, setItem: Boolean) {
        val iS = background.itemStack.clone()

        val menuItem = CustomButton(iS, ArrayList(ownInventorySettings.themeSlot)) { event ->
            viewingPlayer.sendMessage("You have taken the background!")
            addEmptyBackground(false)
        }
        addMenuItem(menuItem,setItem)
    }

    private fun addEmptyBackground(setItem: Boolean) {
        addMenuItem(CustomButton(ItemStack(Material.AIR), ArrayList(ownInventorySettings.themeSlot)) { event ->
            val background = NecroNomicon.instance?.backgroundHandler?.getBackground(event.cursor)

            if (background == null) {
                event.isCancelled = true
                viewingPlayer.sendMessage("This item is not a background!")
                return@CustomButton
            }

            viewingPlayer.sendMessage("You have added a background")
            addFilledBackground(background, false)
        },setItem)
    }

}