package yandexschool.dmpolyakov.money.models.converters

import android.arch.persistence.room.TypeConverter
import yandexschool.dmpolyakov.money.OperationCategory

class OperationCategoryConverter {
    @TypeConverter
    fun fromOperationCategory(operationCategory: OperationCategory): String =
            operationCategory.name

    @TypeConverter
    fun toOperationCategory(data: String): OperationCategory {
        for (item in OperationCategory.values()) {
            if (data == item.name)
                return item
        }
        return throw Exception("Unknown operation category")
    }
}