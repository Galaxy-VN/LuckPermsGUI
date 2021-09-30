package io.github.galaxy.internal.menu.groups.editor

import io.github.galaxy.internal.menu.groups.GroupsMenu
import net.luckperms.api.model.group.Group
import org.bukkit.entity.Player
import taboolib.library.xseries.XMaterial
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Basic
import taboolib.platform.util.ItemBuilder

object EditorGroup {

    fun open(player: Player, group: Group) {
        player.openMenu<Basic>("Editor Group ${group.name}") {
            rows(6)
            map(
                "####@###<",
                "",
                " 1  2  3 ",
                ""
            )
            set('#', XMaterial.BLACK_STAINED_GLASS_PANE) {
                name = "§8"
            }
            set('<', XMaterial.PLAYER_HEAD) {
                name = "§cBack"
                skullTexture = ItemBuilder.SkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
            }
            set('@', XMaterial.BOOKSHELF) {
                name = "§6Group Info"
                lore += listOf(
                    "",
                    "§eGroup name:§7 ${group.name}",
                    "§eGroup display name:§7 ${group.friendlyName}",
                    "§eGroup weight:§7 ${group.weight}",
                    "§6Count:",
                    "§8➥§e Nodes:§7 ${group.nodes.size}",
                    "§8➥§e Permissions:§7 ${group.distinctNodes.size}",
                    "§8➥§e Prefixes:§7 ${group.cachedData.metaData.prefixes.size}",
                    "§8➥§e Suffixes:§7 ${group.cachedData.metaData.suffixes.size}",
                    "§6Cached data:",
                    "§8➥§e Prefix:§7 ${group.cachedData.metaData.prefix}",
                    "§8➥§e Suffix:§7 ${group.cachedData.metaData.suffix}"
                )
            }
            set('1', XMaterial.WRITABLE_BOOK) {
                name = "&2Permissions"
                lore += listOf(
                    "&7View all player permissions",
                    "",
                    "&8➥&e Click to edit"
                )
                colored()
            }
            onClick(lock = true) { clickEvent ->
                when (clickEvent.slot) {
                    '<' -> {
                        GroupsMenu.open(player)
                    }
                    '1' -> {
                        EditorPermissions.open(player, group)
                    }
                }
            }
        }
    }
}