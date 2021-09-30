package io.github.galaxy.internal.menu.users

import io.github.galaxy.internal.menu.users.editor.EditorGroups
import io.github.galaxy.internal.menu.users.editor.EditorPermissions
import io.github.galaxy.internal.menu.users.editor.EditorSubGroups
import net.luckperms.api.model.user.User
import net.luckperms.api.node.types.PrefixNode
import net.luckperms.api.node.types.SuffixNode
import org.bukkit.entity.Player
import taboolib.library.xseries.XMaterial
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Basic
import taboolib.platform.util.nextChat
import taboolib.platform.util.sendLang

object UsersEditor {
    
    fun editor(player: Player, user: User) {
        player.openMenu<Basic>("User Editor") {
            rows(6)
            map(
                "####@###<",
                "",
                " 1  2  3 ",
                "",
                "  4"
            )
            set('#', XMaterial.BLACK_STAINED_GLASS_PANE) {
                name = ""
            }
            set('<', XMaterial.PLAYER_HEAD) {
                name = "&cBack"
                skullOwner = "MHF_arrowleft"
                colored()
            }
            set('@', XMaterial.PLAYER_HEAD) {
                name = "&6Player Info"
                lore += listOf(
                    "",
                    "&ePlayer name:&7 ${user.username}",
                    "&ePlayer UUID:&7 ${user.uniqueId}",
                    "&ePlayer Group:&7 ${user.primaryGroup}",
                    "&6Count:",
                    "&8➥&e Nodes:&7 ${user.nodes.size}",
                    "&8➥&e Permissions:&7 ${user.distinctNodes.size}",
                    "&8➥&e Prefixes:&7 ${user.cachedData.metaData.prefixes.size}",
                    "&8➥&e Suffixes:&7 ${user.cachedData.metaData.suffixes.size}",
                    "&6Cached data:",
                    "&8➥&e Current Prefix:&7 ${user.cachedData.metaData.prefix}",
                    "&8➥&e Current Suffix:&7 ${user.cachedData.metaData.suffix}"
                )
                skullOwner = user.username
                colored()
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
            set('2', XMaterial.BOOKSHELF) {
                name = "&6Groups"
                lore += listOf(
                    "&7Choose player groups",
                    "",
                    "&8➥&e Click to edit"
                )
                colored()
            }
            set('3', XMaterial.CHEST_MINECART) {
                name = "&6Sub Groups"
                lore += listOf(
                    "&7Add sub-group for player",
                    "",
                    "&8➥&e Click to edit"
                )
                colored()
            }
            set('4', XMaterial.NAME_TAG) {
                name = "&ePrefix And Suffix"
                lore += listOf(
                    "&7Edit prefix and suffix",
                    "&7for player",
                    "",
                    "&8➥&e Left Click to edit prefix",
                    "&8➥&e Right Click to edit suffix"
                )
                colored()
            }
            onClick(lock = true) {
                when (it.slot) {
                    '<' -> {
                        UsersMenu.open(player)
                    }
                    '1' -> {
                        EditorPermissions.open(player, user)
                    }
                    '2' -> {
                        EditorGroups.open(player, user)
                    }
                    '3' -> {
                        EditorSubGroups.open(player, user)
                    }
                    '4' -> {
                        if (it.clickEvent().isLeftClick) {
                            player.closeInventory()
                            player.sendLang("Player-Edit-Prefix")
                            player.nextChat {
                                user.data().add(PrefixNode.builder(it, 10).build())
                                player.sendLang("Plugin-Success")
                            }
                        }
                        if (it.clickEvent().isRightClick) {
                            player.closeInventory()
                            player.sendLang("Player-Edit-Suffix")
                            player.nextChat {
                                user.data().add(SuffixNode.builder(it, 10).build())
                                player.sendLang("Plugin-Success")
                            }
                        }
                    }
                }
            }
        }
    }
}