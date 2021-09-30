package io.github.galaxy.internal.command

import io.github.galaxy.internal.menu.Main
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand

@CommandHeader(name = "luckpermsgui", aliases = ["lpgui"], permission = "luckpermsgui.access")
internal object Command {

    @CommandBody
    val main = mainCommand {
        execute<Player> { sender, _, _, ->
            Main.main(sender)
        }
    }
}