package xyz.larkyy.necronomicon.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import xyz.larkyy.necronomicon.NecroNomicon

class MainCommand(private val plugin: NecroNomicon): CommandExecutor {
    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        if (p3.isEmpty()) {
            return false
        }

        if (!p0.hasPermission("necronomicon.admin")) {
            p0.sendMessage("§cYou do not have permissions to do that!")
            return false
        }

        when(p3[0].lowercase()) {
            "badge" -> {
                if (p3.size < 4) {
                    p0.sendMessage("§7Usage: /necronomicon badge give <player> <badge>")
                    return false
                }
                if (p3[1].equals("give",true)) {
                    val p = Bukkit.getPlayer(p3[2])
                    if (p == null) {
                        p0.sendMessage("§cUnknown player!")
                        return false
                    }

                    val badge = plugin.badgeHandler.getBadge(p3[3])
                    if (badge == null) {
                        p0.sendMessage("§cUnknown badge id!")
                        return false
                    }
                    p.inventory.addItem(badge.item)
                    p0.sendMessage("§aBadge given!")
                }
            }
            "theme" -> {
                if (p3.size < 4) {
                    p0.sendMessage("§7Usage: /necronomicon theme give <player> <theme>")
                    return false
                }
                if (p3[1].equals("give",true)) {
                    val p = Bukkit.getPlayer(p3[2])
                    if (p == null) {
                        p0.sendMessage("§cUnknown player!")
                        return false
                    }

                    val theme = plugin.themeHandler.getTheme(p3[3])
                    if (theme == null) {
                        p0.sendMessage("§cUnknown theme id!")
                        return false
                    }
                    p.inventory.addItem(theme.itemStack)
                    p0.sendMessage("§aTheme given!")
                }
            }
            "background" -> {
                if (p3.size < 4) {
                    p0.sendMessage("§7Usage: /necronomicon background give <player> <background>")
                    return false
                }
                if (p3[1].equals("give",true)) {
                    val p = Bukkit.getPlayer(p3[2])
                    if (p == null) {
                        p0.sendMessage("§cUnknown player!")
                        return false
                    }

                    val background = plugin.backgroundHandler.getBackground(p3[3])
                    if (background == null) {
                        p0.sendMessage("§cUnknown background id!")
                        return false
                    }
                    p0.sendMessage("§aBackground given!")
                    p.inventory.addItem(background.itemStack)
                }
            }
        }
        return true
    }
}