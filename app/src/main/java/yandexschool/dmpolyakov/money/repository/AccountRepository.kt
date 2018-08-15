package yandexschool.dmpolyakov.money.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import yandexschool.dmpolyakov.money.models.Account


interface AccountRepository {

    fun getAccounts(): Flowable<List<Account>>
    fun addAccount(account: Account): Completable
    fun getAccount(id: Long): Flowable<Account>
    fun renameAccount(accountId: Long, title: String): Completable
    fun updateAccount(account: Account): Completable

}