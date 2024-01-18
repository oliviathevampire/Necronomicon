package xyz.larkyy.necronomicon

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import xyz.larkyy.necronomicon.background.BackgroundHandler
import xyz.larkyy.necronomicon.badge.BadgeHandler
import xyz.larkyy.necronomicon.commands.MainCommand
import xyz.larkyy.necronomicon.commands.ProfileCommand
import xyz.larkyy.necronomicon.loader.ConfigLoader
import xyz.larkyy.necronomicon.menu.MenuListener
import xyz.larkyy.necronomicon.profile.ProfileManager
import xyz.larkyy.necronomicon.theme.ThemeHandler
import xyz.larkyy.necronomicon.util.sendConsoleMessage

class NecroNomicon : JavaPlugin() {

    lateinit var configLoader: ConfigLoader
    lateinit var badgeHandler: BadgeHandler
    lateinit var themeHandler: ThemeHandler
    lateinit var backgroundHandler: BackgroundHandler
    lateinit var profileManager: ProfileManager

    companion object {
        var instance: NecroNomicon? = null
    }

    override fun onLoad() {
        instance = this

        configLoader = ConfigLoader(this)
        sendConsoleMessage("Configurations loaded!")
        badgeHandler = BadgeHandler(this)
        sendConsoleMessage("Badges loaded!")
        themeHandler = ThemeHandler(this)
        sendConsoleMessage("Themes loaded!")
        backgroundHandler = BackgroundHandler(this)
        sendConsoleMessage("Backgrounds loaded!")
        profileManager = ProfileManager(this)
        sendConsoleMessage("Profiles Manager initialized!")
    }

    fun reloadConfigs() {
        configLoader = ConfigLoader(this)
        sendConsoleMessage("Configurations loaded!")
        badgeHandler = BadgeHandler(this)
        sendConsoleMessage("Badges loaded!")
        themeHandler = ThemeHandler(this)
        sendConsoleMessage("Themes loaded!")
        backgroundHandler = BackgroundHandler(this)
        sendConsoleMessage("Backgrounds loaded!")
    }

    override fun onEnable() {
        getCommand("necronomicon")?.setExecutor(MainCommand(this)).apply {
            sendConsoleMessage("Necronomicon command registered!")
        }
        getCommand("necronomicon")?.setTabCompleter { _, _, _, args -> tabComplete(args) }

        getCommand("profile")?.setExecutor(ProfileCommand(this)).apply {
            sendConsoleMessage("Profile command registered!")
        }
        sendConsoleMessage("<white>[NecroNomicon] Plugin has been <green>ENABLED<white>!")
        server.pluginManager.registerEvents(MenuListener(),this)
    }


    private fun tabComplete(args: Array<out String>): MutableList<String> {
        if (args.size > 1 && args[1].equals("reload", ignoreCase = true)) {
            return mutableListOf() // Return an empty list for no further completions
        }
        return when (args.size) {
            1 -> return mutableListOf("badge", "theme", "background", "reload")
            2 -> return mutableListOf("give")
            3 -> Bukkit.getOnlinePlayers().map { it.name }.toMutableList()
            4 -> when(args[0].lowercase()) {
                "badge" -> return badgeHandler.badges.keys.toMutableList()
                "theme" -> return themeHandler.themes.keys.toMutableList()
                "background" -> return backgroundHandler.backgrounds.keys.toMutableList()
                else -> mutableListOf()
            }
            else -> mutableListOf() // Default case
        }
    }

    override fun onDisable() {
        sendConsoleMessage("<white>[NecroNomicon] Plugin has been <red>DISABLED<white>!")
    }

}