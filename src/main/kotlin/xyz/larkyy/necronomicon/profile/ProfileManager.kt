package xyz.larkyy.necronomicon.profile

import org.bukkit.entity.Player
import xyz.larkyy.necronomicon.NecroNomicon
import xyz.larkyy.necronomicon.menu.OthersInventorySettings
import xyz.larkyy.necronomicon.menu.OwnInventorySettings
import xyz.larkyy.necronomicon.menu.menus.OthersProfileMenu
import xyz.larkyy.necronomicon.menu.menus.OwnProfileMenu
import java.util.*
import java.util.concurrent.CompletableFuture

class ProfileManager(val plugin: NecroNomicon) {

    private val ownInventorySettings: OwnInventorySettings = plugin.configLoader.loadOwnProfileSettings()
    private val othersInventorySettings: OthersInventorySettings = plugin.configLoader.loadOthersProfileSettings()
    private val profileHandler = ProfileHandler(plugin)


    fun openProfile(player: Player, profile: PlayerProfile) {
        if (profile.uuid == player.uniqueId) {
            openOwnProfile(player,profile)
        } else {
            openSomeonesProfile(player,profile)
        }
    }

    private fun openOwnProfile(player: Player, playerProfile: PlayerProfile) {
        OwnProfileMenu(ownInventorySettings,player,playerProfile)
    }

    private fun openSomeonesProfile(player: Player, playerProfile: PlayerProfile) {
        OthersProfileMenu(othersInventorySettings,player,playerProfile)
    }

    fun createProfile(player: Player): CompletableFuture<PlayerProfile> {
        return profileHandler.createProfile(player)
    }

    fun saveProfile(playerProfile: PlayerProfile): CompletableFuture<Void> {
        return profileHandler.saveProfile(playerProfile)
    }

    fun getProfile(player: Player): CompletableFuture<PlayerProfile> {

        val future = CompletableFuture<PlayerProfile>()

        loadProfile(player.uniqueId).thenAccept { profile ->
            if (profile == null) {
                createProfile(player).thenAccept { newProfile ->
                    future.completeAsync { newProfile }
                }
                return@thenAccept
            }
            future.completeAsync {
                profile
            }
        }
        return future
    }

    fun loadProfile(uuid: UUID): CompletableFuture<PlayerProfile?> {
        return profileHandler.loadProfile(uuid)
    }

    fun loadProfile(username: String): CompletableFuture<PlayerProfile?> {
        return profileHandler.loadProfile(username)
    }
}