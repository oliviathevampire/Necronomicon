package xyz.larkyy.necronomicon.theme

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import xyz.larkyy.necronomicon.NecroNomicon

class ThemeHandler(plugin: NecroNomicon) {

    private val namespacedKey: NamespacedKey
    val themes: MutableMap<String, Theme>

    init {
        this.namespacedKey = NamespacedKey(plugin, "theme-id")
        this.themes = plugin.configLoader.loadThemes()
    }

    fun isTheme(item: ItemStack): Boolean {
        val im = item.itemMeta ?: return false
        val pdc = im.persistentDataContainer
        if (!pdc.has(namespacedKey)) return false
        val id = pdc.get(namespacedKey, PersistentDataType.STRING) ?: return false
        return isTheme(id)
    }

    fun isTheme(id: String): Boolean {
        return themes.containsKey(id)
    }

    fun getTheme(id: String): Theme? {
        return themes[id]
    }
    fun getTheme(item: ItemStack): Theme? {
        val im = item.itemMeta ?: return null
        val pdc = im.persistentDataContainer
        if (!pdc.has(namespacedKey)) return null
        val id = pdc.get(namespacedKey, PersistentDataType.STRING) ?: return null
        return themes[id]
    }

    fun createTheme(id: String, item: ItemStack, value: String, length: Int) {
        val im = item.itemMeta ?: return
        val pdc = im.persistentDataContainer
        pdc.set(namespacedKey, PersistentDataType.STRING, id)
        item.setItemMeta(im)

        themes[id] = Theme(id, item, value, length)
    }
}