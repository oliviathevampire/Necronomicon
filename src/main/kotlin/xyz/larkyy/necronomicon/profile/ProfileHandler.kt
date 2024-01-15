package xyz.larkyy.necronomicon.profile

import org.bukkit.entity.Player
import xyz.larkyy.necronomicon.NecroNomicon
import xyz.larkyy.necronomicon.util.sendConsoleMessage
import java.util.*
import java.util.concurrent.CompletableFuture

class ProfileHandler(val plugin: NecroNomicon) {

    private val database = DatabaseHandler(plugin)

    fun createProfile(player: Player): CompletableFuture<PlayerProfile> {
        val profile = PlayerProfile(player.uniqueId,player.name, ArrayList(),null,null,null,0)

        return saveProfile(profile).thenApply {
            plugin.sendConsoleMessage("New profile saved")
            return@thenApply profile
        }
    }

    fun saveProfile(playerProfile: PlayerProfile): CompletableFuture<Void> {
        return database.saveProfile(playerProfile)
    }

    fun loadProfile(uuid: UUID): CompletableFuture<PlayerProfile?> {
        return database.loadProfile(uuid)
    }fun loadProfile(username: String): CompletableFuture<PlayerProfile?> {
        return database.loadProfile(username)
    }

}