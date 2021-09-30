package io.github.galaxy.util

import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

object Time {

    fun getTime(time: Long): String {
        var time = time.minus(System.currentTimeMillis())

        var tempSec = time / 1000
        var sec = tempSec % 60
        var min = (tempSec / 60) % 60
        var hour = (tempSec / (60 * 60)) % 24
        var temphour = (tempSec / (60*60))
        var days = 0
        while (temphour >= 24) {
            days++
            temphour -= 24
        }

        return "$days day(s) $hour hour(s) $min minute(s) $sec second(s)"
    }

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    val formatDate: () -> String = { dateFormat.format(System.currentTimeMillis()) }

    /**
     * @author Bkm016
     */
    fun parseDuration(string: String): Duration {
        var dur = string.uppercase(Locale.ENGLISH)
        if (!dur.contains("T")) {
            if (dur.contains("D")) {
                if (dur.contains("H") || dur.contains("M") || dur.contains("S")) dur = dur.replace("D", "DT")
            } else if (dur.startsWith("P")) dur = "PT" + dur.substring(1)
            else dur = "T$dur"
        }
        if (!dur.startsWith("P")) dur = "P$dur"

        return Duration.parse(dur)
    }
}