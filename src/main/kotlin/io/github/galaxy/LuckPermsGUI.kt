package io.github.galaxy

import org.bukkit.Bukkit
import taboolib.common.env.Repository.downloadToFile
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.common.platform.function.pluginVersion
import taboolib.module.configuration.Config
import taboolib.module.configuration.SecuredFile
import taboolib.module.lang.sendLang
import taboolib.platform.BukkitPlugin
import java.io.File
import java.io.IOException
import java.net.URL

object LuckPermsGUI : Plugin() {

    @Config("settings.yml",true, autoReload = true)
    lateinit var SETTINGS: SecuredFile
        private set

    val plugin by lazy { BukkitPlugin.getInstance() }

    val motd = arrayOf(
        "",
        "§2.____                   __   __________                              §a________ ____ ___.___ ",
        "§2|    |    __ __   ____ |  | _\\______   \\ ___________  _____   ______§a/  _____/|    |   \\   |",
        "§2|    |   |  |  \\_/ ___\\|  |/ /|     ___// __ \\_  __ \\/     \\ /  ___§a/   \\  ___|    |   /   |",
        "§2|    |___|  |  /\\  \\___|    < |    |   \\  ___/|  | \\/  Y Y  \\\\___ \\§a\\    \\_\\  \\    |  /|   |",
        "§2|_______ \\____/  \\___  >__|_ \\|____|    \\___  >__|  |__|_|  /____  >§a\\______  /______/ |___|",
        "§2        \\/           \\/     \\/              \\/            \\/     \\/§a        \\/              ",
    )

    override fun onLoad() {
        motd.forEach { l -> console().sendMessage(l) }
        console().sendLang("Plugin-Loaded", Bukkit.getVersion())

        if (!hookLuckPerms()) {
            return
        }
    }

    override fun onEnable() {
        console().sendLang("Plugin-Enabled", pluginVersion)
    }

    override fun onDisable() {
        console().sendLang("Plugin-Disabled")
    }

    /*
    * Detection front LuckPerms
    * And automatically download and restart the server
    */
    private fun hookLuckPerms(): Boolean {
        val plugin = Bukkit.getPluginManager().getPlugin("LuckPerms")
        val jarFile = File("plugins/LuckPerms.jar")
        val url = URL("https://api.spiget.org/v2/resources/28140/download")

        if (plugin == null) {
            jarFile.delete()
            console().sendLang("Plugin-Depend-Download", "LuckPerms")
            try {
                downloadToFile(url, jarFile)
            } catch (e: IOException) {
                e.printStackTrace()
                console().sendLang("Plugin-Depend-Install-Failed", "LuckPerms")
                Bukkit.shutdown()
                return false
            }
            console().sendLang("Plugin-Depend-Installed", "LuckPerms")
            Bukkit.shutdown()
            return false
        }
        return true
    }
}