package xyz.larkyy.necronomicon.util

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.sendConsoleMessage(message: String) {
    Bukkit.getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize(message))
}