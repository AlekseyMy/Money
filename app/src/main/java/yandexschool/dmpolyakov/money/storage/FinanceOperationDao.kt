package yandexschool.dmpolyakov.money.storage

import android.arch.persistence.room.*
import io.reactivex.Flowable
import yandexschool.dmpolyakov.money.models.FinanceOperation

@Dao
interface FinanceOperationDao {
    @Query("SELECT * FROM financeoperation")
    fun getAll(): Flowable<List<FinanceOperation>>

    @Query("SELECT * FROM financeoperation WHERE state = :state AND account_id = :accountId")
    fun getFinanceOperationsByIdAndInState(accountId: Long,
                                           state: Int): Flowable<List<FinanceOperation>>

    @Query("SELECT * FROM financeoperation WHERE id = :id")
    fun getById(id: Long): Flowable<FinanceOperation>

    @Query("SELECT * FROM financeoperation  WHERE account_id = :accountId")
    fun getByAccountId(accountId: Long): Flowable<List<FinanceOperation>>

    @Query("SELECT * FROM financeoperation WHERE state = :state AND timeFinish < :timeNow")
    fun getPeriodicFinanceOperations(timeNow: Long, state: Int): Flowable<List<FinanceOperation>>

    @Insert
    fun insert(financeOperation: FinanceOperation)

    @Update
    fun update(financeOperation: FinanceOperation)

    @Delete
    fun delete(financeOperation: FinanceOperation)
}