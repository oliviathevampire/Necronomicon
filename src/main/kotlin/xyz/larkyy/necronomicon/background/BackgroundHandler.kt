package xyz.larkyy.necronomicon.background

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import xyz.larkyy.necronomicon.NecroNomicon

class BackgroundHandler(plugin: NecroNomicon) {

    private val namespacedKey: NamespacedKey
    val backgrounds = HashMap<String, Background>()

    init {
        this.namespacedKey = NamespacedKey(plugin, "background-id")
    }

    fun isBackground(item: ItemStack): Boolean {
        val im = item.itemMeta ?: return false
        val pdc = im.persistentDataContainer
        if (!pdc.has(namespacedKey)) return false
        val id = pdc.get(namespacedKey, PersistentDataType.STRING) ?: return false
        return isBackground(id)
    }

    fun isBackground(id: String): Boolean {
        return backgrounds.containsKey(id)
    }

    fun getBackground(id: String): Background? {
        return backgrounds[id]
    }

    fun getBackground(item: ItemStack): Background? {
        val im = item.itemMeta ?: return null
        val pdc = im.persistentDataContainer
        if (!pdc.has(namespacedKey)) return null
        val id = pdc.get(namespacedKey, PersistentDataType.STRING) ?: return null
        return backgrounds[id]
    }

    fun createBackground(id: String, item: ItemStack, value: String, length: Int) {
        val im = item.itemMeta ?: return
        val pdc = im.persistentDataContainer
        pdc.set(namespacedKey, PersistentDataType.STRING, id)
        item.setItemMeta(im)

        backgrounds[id] = Background(id, item, value, length)
    }
}