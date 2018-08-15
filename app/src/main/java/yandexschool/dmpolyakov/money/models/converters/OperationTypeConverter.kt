package yandexschool.dmpolyakov.money.models.converters

import android.arch.persistence.room.TypeConverter
import yandexschool.dmpolyakov.money.OperationType

class OperationTypeConverter {
    @TypeConverter
    fun fromOperationType(operationType: OperationType): String = operationType.name

    @TypeConverter
    fun toOperationType(data: String): OperationType =
            when (data) {
                OperationType.Income.name -> OperationType.Income
                OperationType.Expense.name -> OperationType.Expense
                else -> throw Exception("Unknown operation type")
            }
}