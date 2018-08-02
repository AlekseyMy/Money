package yandexschool.dmpolyakov.money.storage

import android.arch.persistence.room.*
import yandexschool.dmpolyakov.money.models.FinanceOperation

@Dao
interface FinanceOperationDao {
    @Query("SELECT * FROM financeoperation")
    fun getAll(): List<FinanceOperation>

    @Query("SELECT * FROM financeoperation WHERE id = :id")
    fun getById(id: Long): FinanceOperation

    @Insert
    fun insert(financeOperation: FinanceOperation)

    @Update
    fun update(financeOperation: FinanceOperation)

    @Delete
    fun delete(financeOperation: FinanceOperation)
}