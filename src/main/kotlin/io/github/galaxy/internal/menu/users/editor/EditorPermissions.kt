package io.github.galaxy.internal.menu.users.editor

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import io.github.galaxy.internal.menu.Main
import io.github.galaxy.internal.menu.users.UsersEditor
import io.github.galaxy.util.Time
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.context.DefaultContextKeys
import net.luckperms.api.model.user.User
import net.luckperms.api.node.Node
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.function.submit
import taboolib.common5.Coerce
import taboolib.library.xseries.XMaterial
import taboolib.module.nms.sendPacket
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Linked
import taboolib.platform.util.*
import java.util.*

object EditorPermissions {

    fun open(player: Player, user: User) {
        player.openMenu<Linked<Node>>("User Permissions") {
            rows(6)
            slots(
                listOf(
                    9, 10, 11, 12, 13, 14, 15, 16, 17,
                    18, 19, 20, 21, 22, 23, 24, 25, 26,
                    27, 28, 29, 30, 31, 32, 33, 34, 35,
                    36, 37, 38, 39, 40, 41, 42, 43, 44
                )
            )
            elements { user.distinctNodes.map { it.type.cast(it) } }
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
            set(4, buildItem(XMaterial.PLAYER_HEAD) {
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
                AddPermissions.displayFor(player, user)
            }
            set(8, buildItem(XMaterial.PLAYER_HEAD) {
                name = "§cBack"
                skullTexture = ItemBuilder.SkullTexture(
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ=="
                )
            }) {
                UsersEditor.editor(player, user)
            }
            onGenerate() { _, element, _, _ ->
                buildItem(XMaterial.WRITABLE_BOOK) {
                    name = "&e${element.key}"
                    lore += listOf(
                        "",
                        "&7Expires in:&e ${
                            if (element.hasExpiry()) Time.getTime(element.expiry!!.toEpochMilli()) else player.asLangText(
                                "Never"
                            )
                        }",
                        "&7Value:&e ${element.value}",
                        "&7Server:&e ${
                            element.contexts.getAnyValue(DefaultContextKeys.SERVER_KEY)
                                .orElse(player.asLangText("Global"))
                        }",
                        "&7World:&e ${
                            element.contexts.getAnyValue(DefaultContextKeys.WORLD_KEY)
                                .orElse(player.asLangText("Global"))
                        }",
                        "",
                        "&7➥&c Click to remove"
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
            onClick { _, element ->
                if (element.hasExpiry()) user.data().remove(Node.builder(element.key).expiry(element.expiryDuration).build())
                else user.data().remove(Node.builder(element.key).build())
                LuckPermsProvider.get().userManager.saveUser(user)
                player.sendLang("Plugin-Success")
                open(player, user)
            }
        }
    }
}