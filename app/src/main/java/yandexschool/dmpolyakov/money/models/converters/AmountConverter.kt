package yandexschool.dmpolyakov.money.models.converters

import android.arch.persistence.room.TypeConverter
import java.math.BigDecimal

class AmountConverter {
    @TypeConverter
    fun fromBigDecimal(amount: BigDecimal): String = amount.toString()

    @TypeConverter
    fun toBigDecimal(data: String): BigDecimal = BigDecimal(data)
}