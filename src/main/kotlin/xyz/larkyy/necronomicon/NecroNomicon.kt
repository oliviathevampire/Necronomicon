package xyz.larkyy.necronomicon

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

    override fun onEnable() {
        getCommand("necronomicon")?.setExecutor(MainCommand(this)).apply {
            sendConsoleMessage("Necronomicon command registered!")
        }
        getCommand("profile")?.setExecutor(ProfileCommand(this)).apply {
            sendConsoleMessage("Profile command registered!")
        }
        sendConsoleMessage("<white>[NecroNomicon] Plugin has been <green>ENABLED<white>!")
        server.pluginManager.registerEvents(MenuListener(),this)
    }

    override fun onDisable() {
        sendConsoleMessage("<white>[NecroNomicon] Plugin has been <red>DISABLED<white>!")
    }

}