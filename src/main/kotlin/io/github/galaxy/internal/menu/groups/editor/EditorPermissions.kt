package io.github.galaxy.internal.menu.groups.editor

import io.github.galaxy.internal.menu.groups.editor.AddPermissions
import net.luckperms.api.model.group.Group
import net.luckperms.api.node.Node
import org.bukkit.entity.Player
import taboolib.library.xseries.XMaterial
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Linked
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.buildItem

object EditorPermissions {

    fun open(player: Player, group: Group) {
        player.openMenu<Linked<Node>>("Group Permissions") {
            rows(6)
            slots(
                listOf(
                    9, 10, 11, 12, 13, 14, 15, 16, 17,
                    18, 19, 20, 21, 22, 23, 24, 25, 26,
                    27, 28, 29, 30, 31, 32, 33, 34, 35,
                    36, 37, 38, 39, 40, 41, 42, 43, 44
                )
            )
            elements { group.distinctNodes.map { it.type.cast(it) } }
            setPreviousPage(47) { _, hasPreviousPage ->
                if (hasPreviousPage) {
                    buildItem(XMaterial.PLAYER_HEAD) {
                        name = "§fTrang trước"
                        skullOwner = "MHF_ArrowLeft"
                    }
                } else {
                    buildItem(XMaterial.BLACK_STAINED_GLASS_PANE) {
                        name = "§8Trang trước"
                    }
                }
            }
            setNextPage(51) { _, hasNextPage ->
                if (hasNextPage) {
                    buildItem(XMaterial.PLAYER_HEAD) {
                        name = "§fTrang tiếp theo"
                        skullOwner = "MHF_ArrowRight"
                    }
                } else {
                    buildItem(XMaterial.BLACK_STAINED_GLASS_PANE) {
                        name = "§8Trang tiếp theo"
                    }
                }
            }
            set(4, buildItem(XMaterial.BOOKSHELF) {
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
            })
            set(52, buildItem(XMaterial.PLAYER_HEAD) {
                skullTexture = ItemBuilder.SkullTexture(
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA1NmJjMTI0NGZjZmY5OTM0NGYxMmFiYTQyYWMyM2ZlZTZlZjZlMzM1MWQyN2QyNzNjMTU3MjUzMWYifX19"
                )
                name = "&aAdd Permission"
                lore += listOf(
                    "&7Add permission",
                    "",
                    "&8➥&3 Click to add."
                )
                colored()
            }) {
                AddPermissions.open(player, group)
            }
            set(8, buildItem(XMaterial.PLAYER_HEAD) {
                name = "§cBack"
                skullTexture = ItemBuilder.SkullTexture(
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ=="
                )
            }) {
                EditorGroup.open(player, group)
            }
        }
    }
}