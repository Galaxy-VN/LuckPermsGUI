package io.github.galaxy.internal.menu.users

import net.luckperms.api.LuckPermsProvider
import org.bukkit.entity.Player
import taboolib.common.platform.function.onlinePlayers
import taboolib.library.xseries.XMaterial
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Linked
import taboolib.platform.util.buildItem
import taboolib.platform.util.inventoryCenterSlots

object UsersMenu {

    fun open(player: Player) {
        player.openMenu<Linked<Player>>("Users Menu") {
            rows(6)
            slots(inventoryCenterSlots)
            elements { onlinePlayers().map { it.cast() }}
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
            onGenerate { _, element, _, _ ->
                buildItem(XMaterial.PLAYER_HEAD) {
                    skullOwner = element.name
                    name = "&ePlayer ${element.name}"
                    lore += listOf(
                        "",
                        "&a➦ Nhấn để quản lí",
                        ""
                    )
                    colored()
                }
            }
            onClick { event, element ->
                UsersEditor.editor(event.clicker, LuckPermsProvider.get().userManager.getUser(element.uniqueId)!!)
            }
        }
    }
}