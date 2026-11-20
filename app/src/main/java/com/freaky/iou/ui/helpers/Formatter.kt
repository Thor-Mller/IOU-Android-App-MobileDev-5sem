package com.freaky.iou.ui.helpers

import java.math.RoundingMode
import java.util.Locale

fun Int.toKrString(showKr: Boolean = true): String {
    val whole = this / 100
    val rem = this % 100
    val result = if (rem == 0) "$whole" else "%d.%02d".format(Locale.US, whole, rem)
    return result + if (showKr) " kr." else ""
}

fun formatPercent(bps: Int): String {
    return if (bps % 100 == 0) {
        "${bps / 100}%"
    } else {
        "%.2f%%".format(Locale.US, bps / 100.0)
    }
}

fun parseToCents(text: String): Int? {
    val normalized = text.trim().replace(',', '.')
    return normalized.toBigDecimalOrNull()
        ?.movePointRight(2)
        ?.setScale(0, RoundingMode.HALF_UP)
        ?.toLong()
        ?.toInt()
}