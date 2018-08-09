package yandexschool.dmpolyakov.money.storage

import android.arch.persistence.room.*
import io.reactivex.Flowable
import yandexschool.dmpolyakov.money.models.FinanceOperationPattern

@Dao
interface FinanceOperationPatternDao {

    @Query("SELECT * FROM financeoperationpattern")
    fun getAll(): Flowable<List<FinanceOperationPattern>>

    @Query("SELECT * FROM financeoperationpattern WHERE id = :id")
    fun getById(id: Long): Flowable<FinanceOperationPattern>

    @Insert
    fun insert(financeOperationPattern: FinanceOperationPattern)

    @Update
    fun update(financeOperationPattern: FinanceOperationPattern)

    @Delete
    fun delete(financeOperationPattern: FinanceOperationPattern)
}