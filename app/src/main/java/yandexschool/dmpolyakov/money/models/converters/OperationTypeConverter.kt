package yandexschool.dmpolyakov.money.models.converters

import android.arch.persistence.room.TypeConverter
import yandexschool.dmpolyakov.money.OperationType
import yandexschool.dmpolyakov.money.R

class OperationTypeConverter {
    @TypeConverter
    fun fromOperationType(operationType: OperationType): Int = operationType.type

    @TypeConverter
    fun toOperationType(data: Int): OperationType =
            when (data) {
                R.string.Income -> OperationType.Income
                R.string.Expense -> OperationType.Expense
                else -> throw Exception("Unknown operation type")
            }
}