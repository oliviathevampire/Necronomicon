package xyz.larkyy.necronomicon

import org.bukkit.plugin.java.JavaPlugin
import xyz.larkyy.necronomicon.background.BackgroundHandler
import xyz.larkyy.necronomicon.badge.BadgeHandler
import xyz.larkyy.necronomicon.commands.MainCommand
import xyz.larkyy.necronomicon.commands.ProfileCommand
import xyz.larkyy.necronomicon.loader.ConfigLoader
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
        badgeHandler = BadgeHandler(this)
        themeHandler = ThemeHandler(this)
        backgroundHandler = BackgroundHandler(this)
        profileManager = ProfileManager(this)

        getCommand("necronomicon")?.setExecutor(MainCommand(this))
        getCommand("profile")?.setExecutor(ProfileCommand(this))
    }

    override fun onEnable() {
        sendConsoleMessage("<white>[NecroNomicon] Plugin has been <green>ENABLED<white>!")
    }

    override fun onDisable() {
        sendConsoleMessage("<white>[NecroNomicon] Plugin has been <red>DISABLED<white>!")
    }

}