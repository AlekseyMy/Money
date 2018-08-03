package yandexschool.dmpolyakov.money.utils

fun timeNow(): Long = System.currentTimeMillis()

fun String.daysToMillis(): Long =
        if (this != null && this != "") {
            (this.toLong()) * 60L * 60L * 1000L
        } else {
            0L
        }