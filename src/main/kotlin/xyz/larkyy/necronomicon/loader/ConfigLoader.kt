package xyz.larkyy.necronomicon.loader

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.inventory.ItemStack
import xyz.larkyy.necronomicon.NecroNomicon
import xyz.larkyy.necronomicon.background.Background
import xyz.larkyy.necronomicon.badge.Badge
import xyz.larkyy.necronomicon.menu.MenuItem
import xyz.larkyy.necronomicon.menu.OthersInventorySettings
import xyz.larkyy.necronomicon.menu.OwnInventorySettings
import xyz.larkyy.necronomicon.menu.items.CustomButton
import xyz.larkyy.necronomicon.menu.items.ReputationButton
import xyz.larkyy.necronomicon.menu.items.StatusButton
import xyz.larkyy.necronomicon.theme.Theme

class ConfigLoader(plugin: NecroNomicon) {

    private val badgesConfig: Config = Config(plugin, "badges.yml")
    private val themesConfig: Config = Config(plugin, "themes.yml")
    private val backgroundsConfig: Config = Config(plugin, "backgrounds.yml")

    private val ownProfileConfig: Config = Config(plugin, "own-profile-menu.yml")
    private val othersProfileConfig: Config = Config(plugin, "others-profile-menu.yml")

    init {
        badgesConfig.load()
        themesConfig.load()
        backgroundsConfig.load()

        ownProfileConfig.load()
        othersProfileConfig.load()
    }

    fun loadOwnProfileSettings(): OwnInventorySettings {
        val cfg = ownProfCfg()
        val path = "own-profile"
        val title = cfg.getString("$path.title", " ")!!
        val size = cfg.getInt("$path.size")

        val statusIs = loadItemStack(cfg, "$path.status-button")
        val statusSlots = cfg.getIntegerList("$path.status-button.slots")
        val statusBtn = StatusButton(statusIs, statusSlots)

        val themeSlot = cfg.getInt("$path.theme-slot")
        val bgSlot = cfg.getInt("$path.background-slot")
        val badgeSlots = cfg.getIntegerList("$path.badge-slots")

        val menuItems = loadMenuItems(cfg, "$path.items")

        return OwnInventorySettings(title, size, statusBtn, themeSlot, bgSlot, badgeSlots, menuItems)
    }

    fun loadOthersProfileSettings(): OthersInventorySettings {
        val cfg = othProfCfg()
        val path = "others-profile"
        val title = cfg.getString("$path.title", " ")!!
        val size = cfg.getInt("$path.size")

        val reputationIs = loadItemStack(cfg, "$path.reputation-button")
        val reputationSlots = cfg.getIntegerList("$path.reputation-button.slots")
        val reputationButton = ReputationButton(reputationIs, reputationSlots)

        val themeSlot = cfg.getInt("$path.theme-slot")
        val bgSlot = cfg.getInt("$path.background-slot")
        val badgeSlots = cfg.getIntegerList("$path.badge-slots")

        val menuItems = loadMenuItems(cfg, "$path.items")

        return OthersInventorySettings(title, size, reputationButton, themeSlot, bgSlot, badgeSlots, menuItems)
    }

    private fun loadMenuItems(cfg: FileConfiguration, path: String): MutableList<MenuItem> {
        return cfg.getConfigurationSection(path)?.getKeys(false)?.map { id ->
            val itemStack = loadItemStack(cfg, "$path.$id")
            val slots = cfg.getIntegerList("$path.$id.slots")
            val commands = cfg.getStringList("$path.$id.commands")

            return@map CustomButton(itemStack, slots) {
                commands.forEach { cmd ->
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd)
                }
                it.isCancelled = true
            }
        }?.toMutableList() ?: ArrayList()
    }

    fun loadBadges(): MutableMap<String, Badge> {
        return badgesCfg().getConfigurationSection("badges")?.getKeys(false)?.associate { id ->
            val path = "badges.$id"
            val itemStack = loadItemStack(badgesCfg(), path)

            id to Badge(id, itemStack)
        }?.toMutableMap() ?: HashMap()
    }

    fun loadThemes(): MutableMap<String, Theme> {
        return themesCfg().getConfigurationSection("themes")?.getKeys(false)?.associate { id ->
            val path = "themes.$id"
            val itemStack = loadItemStack(themesCfg(), "$path.item")
            val value = themesCfg().getString("$path.value", "")!!
            val length = themesCfg().getInt("$path.length")

            id to Theme(id, itemStack, value, length)
        }?.toMutableMap() ?: HashMap()
    }

    fun loadBackgrounds(): MutableMap<String, Background> {
        return bgCfg().getConfigurationSection("backgrounds")?.getKeys(false)?.associate { id ->
            val path = "backgrounds.$id"
            val itemStack = loadItemStack(bgCfg(), "$path.item")
            val value = bgCfg().getString("$path.value", "")!!
            val length = bgCfg().getInt("$path.length")

            id to Background(id, itemStack, value, length)
        }?.toMutableMap() ?: HashMap()
    }

    private fun loadItemStack(cfg: FileConfiguration, path: String): ItemStack {
        val material = Material.valueOf(cfg.getString("$path.material", "STONE")!!.uppercase())
        val displayName = cfg.getString("$path.display-name")!!
        val miniMessage = MiniMessage.builder().tags(TagResolver.builder().resolver(StandardTags.defaults()).build()).build()

        val itemStack = ItemStack(material)
        val im = itemStack.itemMeta ?: return itemStack
        im.displayName(miniMessage.deserialize(displayName))

        if (cfg.contains("$path.lore")) {
            val lore = loadComponentList(miniMessage, cfg, "$path.lore")
            im.lore(lore)
        }
        if (cfg.contains("$path.model-data")) {
            val modelData = cfg.getInt("$path.model-data")
            im.setCustomModelData(modelData)
        }

        itemStack.setItemMeta(im)
        return itemStack
    }

    private fun loadComponentList(miniMessage: MiniMessage, cfg: FileConfiguration, path: String): ArrayList<Component> {
        val list = ArrayList<Component>()
        for (s in cfg.getStringList(path)) {
            list.add(miniMessage.deserialize(s))
        }
        return list
    }

    private fun badgesCfg(): FileConfiguration {
        return badgesConfig.getConfiguration()!!
    }

    private fun themesCfg(): FileConfiguration {
        return themesConfig.getConfiguration()!!
    }

    private fun bgCfg(): FileConfiguration {
        return backgroundsConfig.getConfiguration()!!
    }

    private fun ownProfCfg(): FileConfiguration {
        return ownProfileConfig.getConfiguration()!!
    }

    private fun othProfCfg(): FileConfiguration {
        return othersProfileConfig.getConfiguration()!!
    }

}