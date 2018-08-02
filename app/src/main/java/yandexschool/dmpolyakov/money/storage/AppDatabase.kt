package yandexschool.dmpolyakov.money.storage

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.models.FinanceOperation

@Database(entities = [Account::class, FinanceOperation::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract val accountDao: AccountDao
    abstract val financeOperationDao: FinanceOperationDao
}