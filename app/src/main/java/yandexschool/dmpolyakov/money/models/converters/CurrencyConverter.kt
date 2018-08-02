package yandexschool.dmpolyakov.money.models.converters

import android.arch.persistence.room.TypeConverter
import yandexschool.dmpolyakov.money.Currency

class CurrencyConverter {
    @TypeConverter
    fun fromCurrency(currency: Currency): String = currency.shortTitle

    @TypeConverter
    fun toCurrency(data: String): Currency =
            when (data) {
                Currency.Dollar.shortTitle -> Currency.Dollar
                Currency.Rubble.shortTitle -> Currency.Rubble
                else -> throw Exception("Unknown currency type")
            }
}