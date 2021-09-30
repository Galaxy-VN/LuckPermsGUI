package io.github.galaxy.internal.menu.users.editor

import io.github.galaxy.internal.menu.users.UsersEditor
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.model.group.Group
import net.luckperms.api.model.user.User
import net.luckperms.api.node.types.InheritanceNode
import org.bukkit.entity.Player
import taboolib.library.xseries.XMaterial
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Linked
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.buildItem
import taboolib.platform.util.sendLang

object AddSubGroups {

    fun open(player: Player, user: User) {
        player.openMenu<Linked<Group>>("Choose Group To Add") {
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
                UsersEditor.editor(player, user)
            }
            onGenerate { _, element, _, _ ->
                if (user.getInheritedGroups(user.queryOptions).contains(element)) {
                    buildItem(XMaterial.BOOKSHELF) {
                        name = "§2Group ${element.name}"
                        lore += listOf(
                            "§7This player already got",
                            "§7this group."
                        )
                        shiny()
                    }
                } else {
                    buildItem(XMaterial.BOOKSHELF) {
                        name = "§2Group ${element.name}"
                        lore += listOf(
                            "§7Add this sub-groups",
                            "§7for this player.",
                            "",
                            "§8➥§eClick to add"
                        )
                    }
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
            for (slot in 52..53) {
                set(slot, buildItem(XMaterial.BLACK_STAINED_GLASS_PANE) {
                    name = "§8"
                })
            }
            onClick { _, element ->
                if (user.getInheritedGroups(user.queryOptions).contains(element) && element.name != user.primaryGroup) {
                    user.data().remove(InheritanceNode.builder(element).build())
                    LuckPermsProvider.get().userManager.saveUser(user)
                    player.sendLang("Plugin-Success")
                    open(player, user)
                } else {
                    user.data().add(InheritanceNode.builder(element).build())
                    LuckPermsProvider.get().userManager.saveUser(user)
                    player.sendLang("Plugin-Success")
                    open(player, user)
                }
            }
        }
    }
}