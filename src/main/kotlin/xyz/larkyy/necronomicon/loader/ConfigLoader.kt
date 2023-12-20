package xyz.larkyy.necronomicon.loader

import org.bukkit.configuration.file.FileConfiguration
import xyz.larkyy.necronomicon.NecroNomicon
import xyz.larkyy.necronomicon.background.Background
import xyz.larkyy.necronomicon.badge.Badge
import xyz.larkyy.necronomicon.menu.OthersInventorySettings
import xyz.larkyy.necronomicon.menu.OwnInventorySettings
import xyz.larkyy.necronomicon.theme.Theme

class ConfigLoader(plugin: NecroNomicon) {

    private val badgesConfig: Config = Config(plugin, "badges.yml")
    private val themesConfig: Config = Config(plugin, "themes.yml")
    private val backgroundsConfig: Config = Config(plugin, "backgrounds.yml")

    private val ownProfileConfig: Config = Config(plugin, "own-profile-menu.yml")
    private val othersProfileConfig: Config = Config(plugin, "own-profile-menu.yml")

    init {
        badgesConfig.load()
        themesConfig.load()
        backgroundsConfig.load()

        ownProfileConfig.load()
        othersProfileConfig.load()
    }

    fun loadOwnProfileSettings(): OwnInventorySettings {

    }

    fun loadOthersProfileSettings(): OthersInventorySettings {

    }

    fun loadBadges(): HashMap<String, Badge> {

    }

    fun loadThemes(): HashMap<String, Theme> {

    }

    fun loadBackgrounds(): HashMap<String, Background> {

    }

    private fun badgesCfg(): FileConfiguration {
        return badgesConfig.getConfiguration()!!
    }

    private fun themesCfg(): FileConfiguration {
        return themesConfig.getConfiguration()!!
    }

    private fun bgCfg(): FileConfig;uration {
        return backgroundsConfig.getConfiguration()!!
    }

    private fun ownProfCfg(): FileConfiguration {
        return ownProfileConfig.getConfiguration()!!
    }

    private fun othProfCfg(): FileConfiguration {
        return othersProfileConfig.getConfiguration()!!
    }

}