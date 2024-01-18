package xyz.larkyy.necronomicon.util

import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.sendConsoleMessage(message: String) {
    val miniMessage = MiniMessage.builder().tags(TagResolver.builder().resolver(StandardTags.defaults()).build()).build()
    Bukkit.getConsoleSender().sendMessage(miniMessage.deserialize(message))
}