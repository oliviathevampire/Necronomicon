package xyz.larkyy.necronomicon.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import xyz.larkyy.necronomicon.NecroNomicon
import xyz.larkyy.necronomicon.profile.ProfileManager

class ProfileCommand(private val plugin: NecroNomicon) : CommandExecutor {
    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {

        if (p0 !is Player) return false

        if (p3.isEmpty()) {
            profileManager().getProfile(p0).thenAccept { profile ->
                p0.sendMessage("Opening Own")
                plugin.profileManager.openProfile(p0,profile)
            }
        } else {
            profileManager().loadProfile(p3[1]).thenAccept { profile ->
                if (profile == null) {
                    p0.sendMessage("Unknown player")
                    return@thenAccept
                }

                p0.sendMessage("Opening Someones")
                plugin.profileManager.openProfile(p0,profile)
            }
        }
        return true
    }

    private fun profileManager(): ProfileManager {
        return plugin.profileManager
    }
}