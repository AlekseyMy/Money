package yandexschool.dmpolyakov.money.models.converters

import android.arch.persistence.room.TypeConverter
import yandexschool.dmpolyakov.money.Currency

class CurrencyConverter {
    @TypeConverter
    fun fromCurrency(currency: Currency): String = currency.name

    @TypeConverter
    fun toCurrency(data: String): Currency {
        for (item in Currency.values()) {
            if (item.name == data) {
                return item
            }
        }
        return throw Exception("Unknown currency type")
    }
}