package yandexschool.dmpolyakov.money.storage

import android.arch.persistence.room.*
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.models.converters.AmountConverter
import java.math.BigDecimal

@Dao
@TypeConverters(AmountConverter::class)
abstract class AccountFinanceOperationDao {

    @Insert
    abstract fun insertFinanceOperation(financeOperation: FinanceOperation)

    @Query("UPDATE account SET amount = amount + :amount WHERE id = :accountId")
    abstract fun updateAccount(accountId: Long, amount: BigDecimal)

    @Transaction
    open fun insertFinanceOperationAndUpdateAccount(financeOperation: FinanceOperation,
                                                    accountId: Long,
                                                    amount: BigDecimal) {
        insertFinanceOperation(financeOperation)
        updateAccount(accountId, amount)
    }
}