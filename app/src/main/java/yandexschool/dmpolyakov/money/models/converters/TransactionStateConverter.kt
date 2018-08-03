package yandexschool.dmpolyakov.money.models.converters

import android.arch.persistence.room.TypeConverter
import yandexschool.dmpolyakov.money.R
import yandexschool.dmpolyakov.money.TransactionState

class TransactionStateConverter {
    @TypeConverter
    fun fromTransactionState(transactionState: TransactionState): Int = transactionState.title

    @TypeConverter
    fun toTransactionState(data: Int): TransactionState =
            when (data) {
                R.string.canceled -> TransactionState.Canceled
                R.string.inProgress -> TransactionState.InProgress
                R.string.done -> TransactionState.Done
                else -> throw Exception("Unknown state type")
            }
}