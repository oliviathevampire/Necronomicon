package xyz.larkyy.necronomicon.menu.menus

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import xyz.larkyy.necronomicon.NecroNomicon
import xyz.larkyy.necronomicon.background.Background
import xyz.larkyy.necronomicon.badge.Badge
import xyz.larkyy.necronomicon.menu.CustomMenu
import xyz.larkyy.necronomicon.menu.OwnInventorySettings
import xyz.larkyy.necronomicon.menu.items.CustomButton
import xyz.larkyy.necronomicon.profile.PlayerProfile
import xyz.larkyy.necronomicon.theme.Theme

class OwnProfileMenu(private val ownInventorySettings: OwnInventorySettings, viewingPlayer: Player, playerProfile: PlayerProfile) :
    CustomMenu(viewingPlayer, playerProfile, ownInventorySettings.size, (playerProfile.background ?: "") +(playerProfile.theme ?: ownInventorySettings.title)) {

    init {
        loadBadges()
        addMenuItems(ownInventorySettings.customButtons)
        loadStatusButton()
        loadTheme()
        loadBackground()
    }

    private fun loadStatusButton() {
        val iS = ownInventorySettings.statusButton.itemStack.clone()

        val menuItem = CustomButton(iS, ownInventorySettings.statusButton.slots) { event ->

            object : BukkitRunnable() {
                override fun run() {
                    ownInventorySettings.statusButton.openStatusEditor(viewingPlayer,playerProfile) {
                    }
                }
            }.runTask(NecroNomicon.instance!!)

            event.isCancelled = true
        }
        addMenuItem(menuItem,true)
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
    }

    private fun addFilledBadge(slot: Int, badge: Badge, setItem: Boolean) {
        val iS = badge.item.clone()

        val menuItem = CustomButton(iS, mutableListOf(slot)) { event ->
            if (event.cursor.type != Material.AIR) {
                event.isCancelled = true
                return@CustomButton
            }
            playerProfile.badges.remove(badge.id)
            NecroNomicon.instance?.profileManager?.saveProfile(playerProfile)
            addEmptyBadge(slot, false)
        }
        addMenuItem(menuItem,setItem)
    }

    private fun addEmptyBadge(slot: Int, setItem: Boolean) {
        val slots = mutableListOf(slot)
        addMenuItem(CustomButton(ItemStack(Material.AIR), slots) { event ->
            val badge = NecroNomicon.instance?.badgeHandler?.getBadge(event.cursor)

            if (badge == null) {
                event.isCancelled = true
                return@CustomButton
            }
            if (event.cursor.amount > 1) {
                event.isCancelled = true
                event.inventory.setItem(slot,badge.item)

                viewingPlayer.setItemOnCursor(event.cursor.clone().apply { amount -= 1 })
            }
            playerProfile.badges.add(badge.id)
            NecroNomicon.instance?.profileManager?.saveProfile(playerProfile)
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

        val menuItem = CustomButton(iS, mutableListOf(ownInventorySettings.themeSlot)) { event ->
            if (event.cursor.type != Material.AIR) {
                event.isCancelled = true
                return@CustomButton
            }
            playerProfile.theme = null
            NecroNomicon.instance?.profileManager?.saveProfile(playerProfile)
            NecroNomicon.instance?.profileManager?.openProfile(viewingPlayer,playerProfile)
            //addEmptyTheme(false)
        }
        addMenuItem(menuItem,setItem)
    }

    private fun addEmptyTheme(setItem: Boolean) {
        addMenuItem(CustomButton(ItemStack(Material.AIR), mutableListOf(ownInventorySettings.themeSlot)) { event ->
            val theme = NecroNomicon.instance?.themeHandler?.getTheme(event.cursor)

            if (theme == null) {
                event.isCancelled = true
                return@CustomButton
            }
            if (event.cursor.amount > 1) {
                event.isCancelled = true
                event.inventory.setItem(ownInventorySettings.themeSlot,theme.itemStack)

                viewingPlayer.setItemOnCursor(event.cursor.clone().apply { amount -= 1 })
            }
            playerProfile.theme = theme.id
            NecroNomicon.instance?.profileManager?.saveProfile(playerProfile)
            NecroNomicon.instance?.profileManager?.openProfile(viewingPlayer,playerProfile)
        },setItem)
    }

    private fun loadBackground() {
        val backgroundId = playerProfile.background
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

        val menuItem = CustomButton(iS, mutableListOf(ownInventorySettings.backgroundSlot)) { event ->
            if (event.cursor.type != Material.AIR) {
                event.isCancelled = true
                return@CustomButton
            }
            playerProfile.background = null
            NecroNomicon.instance?.profileManager?.saveProfile(playerProfile)
            NecroNomicon.instance?.profileManager?.openProfile(viewingPlayer,playerProfile)
        }
        addMenuItem(menuItem,setItem)
    }

    private fun addEmptyBackground(setItem: Boolean) {
        addMenuItem(CustomButton(ItemStack(Material.AIR), mutableListOf(ownInventorySettings.backgroundSlot)) { event ->
            val background = NecroNomicon.instance?.backgroundHandler?.getBackground(event.cursor)

            if (background == null) {
                event.isCancelled = true
                return@CustomButton
            }
            if (event.cursor.amount > 1) {
                event.isCancelled = true
                event.inventory.setItem(ownInventorySettings.backgroundSlot,background.itemStack)

                viewingPlayer.setItemOnCursor(event.cursor.clone().apply { amount -= 1 })
            }
            playerProfile.background = background.id
            NecroNomicon.instance?.profileManager?.saveProfile(playerProfile)
            NecroNomicon.instance?.profileManager?.openProfile(viewingPlayer,playerProfile)
        },setItem)
    }

}