package io.github.galaxy.internal.menu.groups

import io.github.galaxy.internal.menu.Main
import io.github.galaxy.internal.menu.groups.editor.EditorGroup
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.model.group.Group
import org.bukkit.entity.Player
import taboolib.library.xseries.XMaterial
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Linked
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.buildItem
import taboolib.platform.util.nextChat
import taboolib.platform.util.sendLang

object GroupsMenu {

    fun open(player: Player) {
        player.openMenu<Linked<Group>>("Groups Menu") {
            rows(6)
            slots(
                listOf(
                    9, 10, 11, 12, 13, 14, 15, 16, 17,
                    18, 19, 20, 21, 22, 23, 24, 25, 26,
                    27, 28, 29, 30, 31, 32, 33, 34, 35,
                    36, 37, 38, 39, 40, 41, 42, 43, 44
                )
            )
            elements { LuckPermsProvider.get().groupManager.loadedGroups.map { it } }
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
            set(8, buildItem(XMaterial.PLAYER_HEAD) {
                name = "§cBack"
                skullTexture = ItemBuilder.SkullTexture(
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ=="
                )
            }) {
                Main.main(player)
            }
            set(52, buildItem(XMaterial.PLAYER_HEAD) {
                name = "§2Create New Groups"
                lore += listOf(
                    "§7Click to create new group",
                    "§7for this server",
                    "",
                    "§8➥§eClick to create"
                )
                skullTexture = ItemBuilder.SkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA1NmJjMTI0NGZjZmY5OTM0NGYxMmFiYTQyYWMyM2ZlZTZlZjZlMzM1MWQyN2QyNzNjMTU3MjUzMWYifX19")
            })  {
                player.closeInventory()
                player.sendLang("Group-Create")
                player.nextChat {
                    LuckPermsProvider.get().groupManager.createAndLoadGroup(it)
                    player.sendLang("Plugin-Success")
                }
            }
            onGenerate { _, element, _, _ ->
                buildItem(XMaterial.BOOKSHELF) {
                    name = "&2Group ${element.name}"
                    lore += listOf(
                        "",
                        "&2Set this group for",
                        "&2player.",
                        "",
                        "&8➥&e Click to edit"
                    )
                    colored()
                }
            }
            for (slot in 0..7) {
                set(slot, buildItem(XMaterial.BLACK_STAINED_GLASS_PANE) {
                    name = "§8"
                })
            }
            for (slot in 45..46) {
                set(slot, buildItem(XMaterial.BLACK_STAINED_GLASS_PANE) {
                    name = "§8"
                })
            }
            for (slot in 48..50) {
                set(slot, buildItem(XMaterial.BLACK_STAINED_GLASS_PANE) {
                    name = "§8"
                })
            }
            set(53, buildItem(XMaterial.BLACK_STAINED_GLASS_PANE) {
                name = "§8"
            })
            onClick { event, element ->
                EditorGroup.open(event.clicker, element)
            }
        }
    }
}