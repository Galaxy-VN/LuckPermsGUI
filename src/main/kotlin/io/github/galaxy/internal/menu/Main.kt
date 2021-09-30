package io.github.galaxy.internal.menu

import io.github.galaxy.internal.menu.groups.GroupsMenu
import io.github.galaxy.internal.menu.users.UsersMenu
import org.bukkit.entity.Player
import taboolib.library.xseries.XMaterial
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Basic
import taboolib.platform.util.ItemBuilder
import java.util.*

object Main {

    fun main(player: Player) {
        player.openMenu<Basic>("§aLuckPerms") {
            rows(3)
            map(
                "",
                " 1  2  3 ",
            )
            set('1', XMaterial.PLAYER_HEAD) {
                name = "§aUsers"
                skullOwner = player.name
            }
            set('2', XMaterial.PLAYER_HEAD) {
                name = "§6Groups"
                skullTexture = ItemBuilder.SkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTJiM2Q1YTZmZWMxZTNhNWYxYzhmM2YzYzk2ZjM4YzY3NDdjMjA5ZDZhMTA2Yzc5YjY1YTNhOWE5Y2ZkOTc3In19fQ==")
            }
            onClick(lock = true) {
                when (it.slot) {
                    '1' -> {
                        UsersMenu.open(player)
                    }
                    '2' -> {
                        GroupsMenu.open(player)
                    }
                }
            }
        }
    }
}