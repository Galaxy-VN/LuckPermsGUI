package io.github.galaxy.internal.menu.groups.editor

import io.github.galaxy.LuckPermsGUI.plugin
import io.github.galaxy.util.Time
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.model.group.Group
import net.luckperms.api.node.Node
import net.luckperms.api.node.types.PermissionNode
import org.bukkit.entity.Player
import org.bukkit.permissions.Permission
import taboolib.common5.Coerce
import taboolib.library.xseries.XMaterial
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Linked
import taboolib.platform.util.buildItem
import taboolib.platform.util.nextChat
import taboolib.platform.util.sendLang

object AddPermissions {

    fun open(player: Player, group: Group) {
        player.openMenu<Linked<Permission>>("Group Permission") {
            rows(6)
            slots(
                listOf(
                    9, 10, 11, 12, 13, 14, 15, 16, 17,
                    18, 19, 20, 21, 22, 23, 24, 25, 26,
                    27, 28, 29, 30, 31, 32, 33, 34, 35,
                    36, 37, 38, 39, 40, 41, 42, 43, 44
                )
            )
            elements { plugin.server.pluginManager.permissions.sortedBy { it.name } }
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
            set(52, buildItem(XMaterial.COMPASS) {
                name = "&aAdd Permission"
                lore += listOf(
                    "&7You can add custom permission",
                    "&7at here",
                    "",
                    "&8➥&3 Click to add."
                )
                colored()
            }) {
                player.closeInventory()
                player.sendLang("Group-Add-Permission")
                player.nextChat {
                    group.data().add(PermissionNode.builder(Coerce.toString(it)).build())
                    LuckPermsProvider.get().groupManager.saveGroup(group)
                    player.sendLang("Plugin-Success")
                }
            }
            onGenerate { _, element, _, _ ->
                buildItem(XMaterial.WRITABLE_BOOK) {
                    name = "§e${element.name}"
                    lore += listOf(
                        "",
                        "§7Add this permission",
                        "§7for group.",
                        "",
                        "§8➥ §cRight-Click to set time",
                        "§8➥ §cLeft-Click to set permanent"
                    )
                }
            }
            for (slot in 0..8) {
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
                if (event.clickEvent().isRightClick) {
                    player.closeInventory()
                    player.sendLang("Group-Time-Permission")
                    player.sendMessage("§eExample: 1d 15m")
                    player.nextChat {
                        group.data().add(Node.builder(element.name).expiry(Time.parseDuration(it.replace(" ", ""))).build())
                        LuckPermsProvider.get().groupManager.saveGroup(group)
                        player.sendLang("Plugin-Success")
                    }
                }
                if (event.clickEvent().isLeftClick) {
                    player.closeInventory()
                    group.data().add(Node.builder(element.name).build())
                    LuckPermsProvider.get().groupManager.saveGroup(group)
                    player.sendLang("Plugin-Success")
                }
            }
        }
    }
}