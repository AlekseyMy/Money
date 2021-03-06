package yandexschool.dmpolyakov.money.storage

import android.arch.persistence.room.*
import io.reactivex.Flowable
import yandexschool.dmpolyakov.money.models.Account

@Dao
interface AccountDao {
    @Query("SELECT * FROM account")
    fun getAll(): Flowable<List<Account>>

    @Query("SELECT * FROM account WHERE id = :id")
    fun getById(id: Long): Flowable<Account>

    @Insert
    fun insert(account: Account)

    @Update
    fun update(account: Account)

    @Query("UPDATE account SET title = :accountTitle WHERE id = :accountId")
    fun updateTitle(accountTitle: String, accountId: Long)

    @Delete
    fun delete(account: Account)
}