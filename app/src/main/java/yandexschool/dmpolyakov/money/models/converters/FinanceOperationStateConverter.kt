package yandexschool.dmpolyakov.money.models.converters

import android.arch.persistence.room.TypeConverter
import yandexschool.dmpolyakov.money.R
import yandexschool.dmpolyakov.money.FinanceOperationState

class FinanceOperationStateConverter {
    @TypeConverter
    fun fromFinanceOperationState(financeOperationState: FinanceOperationState): Int = financeOperationState.code

    @TypeConverter
    fun toFinanceOperationState(data: Int): FinanceOperationState =
            when (data) {
                R.string.canceled -> FinanceOperationState.Canceled
                R.string.inProgress -> FinanceOperationState.InProgress
                R.string.done -> FinanceOperationState.Done
                else -> throw Exception("Unknown state type")
            }
}