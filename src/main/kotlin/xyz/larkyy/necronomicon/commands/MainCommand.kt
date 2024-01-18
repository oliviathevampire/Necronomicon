package xyz.larkyy.necronomicon.commands

import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import xyz.larkyy.necronomicon.NecroNomicon


class MainCommand(private val plugin: NecroNomicon): CommandExecutor {
    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        val miniMessage = MiniMessage.builder().tags(TagResolver.builder().resolver(StandardTags.defaults()).build()).build()

        if (p3.isEmpty()) {
            p0.sendMessage(miniMessage.deserialize("""
                    <#b8bbc2>Available Commands:
                        <hover:show_text:\"<gray>fill badge command\"><click:SUGGEST_COMMAND:/necronomicon badge give ><#6f737d>/necronomicon<#b8bbc2>badge give <#43c9fa>target <#fa8943>type</click></hover>
                        <hover:show_text:\"<gray>fill background command\"><click:SUGGEST_COMMAND:/necronomicon background give ><#6f737d>/necronomicon<#b8bbc2>background give <#43c9fa>target <#fa8943>type</click></hover>
                        <hover:show_text:\"<gray>fill theme command\"><click:SUGGEST_COMMAND:/necronomicon theme give ><#6f737d>/necronomicon<#b8bbc2>theme give <#43c9fa>target <#fa8943>type</click></hover>
                        <hover:show_text:\"<gray>fill reload command\"><click:SUGGEST_COMMAND:/necronomicon reload ><#6f737d>/necronomicon<#b8bbc2>reload</click></hover>
                """.trimIndent()))
            return true
        }

        if (!p0.hasPermission("necronomicon.admin")) {
            p0.sendMessage("§cYou do not have permissions to do that!")
            return false
        }

        when(p3[0].lowercase()) {
            "badge" -> {
                if (p3.size < 4) {
                    p0.sendMessage(miniMessage.deserialize("<red>Usage: /necronomicon badge give <#43c9fa><player> <#fa8943><badge>"))
                    return false
                }
                if (p3[1].equals("give",true)) {
                    val p = Bukkit.getPlayer(p3[2])
                    if (p == null) {
                        p0.sendMessage(miniMessage.deserialize("<red>Unknown player!"))
                        return false
                    }

                    val badge = plugin.badgeHandler.getBadge(p3[3])
                    if (badge == null) {
                        p0.sendMessage(miniMessage.deserialize("<red>Unknown badge id!"))
                        return false
                    }
                    p.inventory.addItem(badge.item)
                    p0.sendMessage(miniMessage.deserialize("<green>Badge given!"))
                }
            }
            "theme" -> {
                if (p3.size < 4) {
                    p0.sendMessage(miniMessage.deserialize("<red>Usage: /necronomicon theme give <#43c9fa><player> <#fa8943><theme>"))
                    return false
                }
                if (p3[1].equals("give",true)) {
                    val p = Bukkit.getPlayer(p3[2])
                    if (p == null) {
                        p0.sendMessage(miniMessage.deserialize("<red>Unknown player!"))
                        return false
                    }

                    val theme = plugin.themeHandler.getTheme(p3[3])
                    if (theme == null) {
                        p0.sendMessage(miniMessage.deserialize("<red>Unknown theme id!"))
                        return false
                    }
                    p.inventory.addItem(theme.itemStack)
                    p0.sendMessage(miniMessage.deserialize("<green>Theme given!"))
                }
            }
            "background" -> {
                if (p3.size < 4) {
                    p0.sendMessage(miniMessage.deserialize("<red>Usage: /necronomicon background give <#43c9fa><player> <#fa8943><background>"))
                    return false
                }
                if (p3[1].equals("give",true)) {
                    val p = Bukkit.getPlayer(p3[2])
                    if (p == null) {
                        p0.sendMessage(miniMessage.deserialize("<red>Unknown player!"))
                        return false
                    }

                    val background = plugin.backgroundHandler.getBackground(p3[3])
                    if (background == null) {
                        p0.sendMessage(miniMessage.deserialize("<red>Unknown background id!"))
                        return false
                    }
                    p0.sendMessage("§aBackground given!")
                    p0.sendMessage(miniMessage.deserialize("<green>Background given!"))
                    p.inventory.addItem(background.itemStack)
                }
            }
            "reload" -> plugin.reloadConfigs()
        }
        return true
    }

}