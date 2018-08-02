package yandexschool.dmpolyakov.money.models.converters

import android.arch.persistence.room.TypeConverter
import yandexschool.dmpolyakov.money.OperationCategory
import yandexschool.dmpolyakov.money.R

class OperationCategoryConverter {
    @TypeConverter
    fun fromOperationCategory(operationCategory: OperationCategory): Int =
            operationCategory.title

    @TypeConverter
    fun toOperationCategory(data: Int) =
            when(data) {
                R.string.category_salary -> OperationCategory.Salary
                R.string.category_gift -> OperationCategory.Gift
                R.string.category_other -> OperationCategory.Other

                R.string.category_transport -> OperationCategory.Transport
                R.string.category_products -> OperationCategory.Products
                R.string.category_health -> OperationCategory.Health
                R.string.category_education -> OperationCategory.Education
                else -> throw Exception("Unknown operation category")
            }
}