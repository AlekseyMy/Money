package yandexschool.dmpolyakov.money.models.converters

import android.arch.persistence.room.TypeConverter
import yandexschool.dmpolyakov.money.FinanceOperationState

class FinanceOperationStateConverter {
    @TypeConverter
    fun fromFinanceOperationState(financeOperationState: FinanceOperationState): String = financeOperationState.name

    @TypeConverter
    fun toFinanceOperationState(data: String): FinanceOperationState =
            when (data) {
                FinanceOperationState.Canceled.name -> FinanceOperationState.Canceled
                FinanceOperationState.InProgress.name -> FinanceOperationState.InProgress
                FinanceOperationState.Done.name -> FinanceOperationState.Done
                else -> throw Exception("Unknown state type")
            }
}