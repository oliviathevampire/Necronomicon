package xyz.larkyy.necronomicon.badge

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import xyz.larkyy.necronomicon.NecroNomicon

class BadgeHandler(plugin: NecroNomicon) {

    private val namespacedKey: NamespacedKey
    val badges: MutableMap<String, Badge>

    init {
        this.namespacedKey = NamespacedKey(plugin, "badge-id")
        this.badges = plugin.configLoader.loadBadges()
    }

    fun isBadge(item: ItemStack): Boolean {
        val im = item.itemMeta ?: return false
        val pdc = im.persistentDataContainer
        if (!pdc.has(namespacedKey)) return false
        val id = pdc.get(namespacedKey, PersistentDataType.STRING) ?: return false
        return isBadge(id)
    }

    fun isBadge(id: String): Boolean {
        return badges.containsKey(id)
    }

    fun getBadge(id: String): Badge? {
        return badges[id]
    }
    fun getBadge(item: ItemStack): Badge? {
        val im = item.itemMeta ?: return null
        val pdc = im.persistentDataContainer
        if (!pdc.has(namespacedKey)) return null
        val id = pdc.get(namespacedKey, PersistentDataType.STRING) ?: return null
        return getBadge(id)
    }

    fun createBadge(id: String, item: ItemStack) {
        val im = item.itemMeta ?: return
        val pdc = im.persistentDataContainer
        pdc.set(namespacedKey, PersistentDataType.STRING, id)
        item.setItemMeta(im)

        badges[id] = Badge(id, item)
    }
}