package yandexschool.dmpolyakov.money.utils

fun timeNow(): Long = System.currentTimeMillis()

fun String.daysToMillis(): Long =
        if (this != null && this != "") {
            (this.toLong()) * 24L * 60L * 60L * 1000L
        } else {
            0L
        }

fun String.secondsToMills(): Long =
        if (this != null && this != "") {
            (this.toLong()) * 1000L
        } else {
            0L
        }

fun Long.millsToDays(): Long =
        (this / 1000L / 60L / 60L / 24L)

fun Long.millsToSeconds(): Long =
        (this / 1000L)