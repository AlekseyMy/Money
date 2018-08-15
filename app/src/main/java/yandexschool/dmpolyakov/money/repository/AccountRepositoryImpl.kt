package yandexschool.dmpolyakov.money.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.storage.AppDatabase
import javax.inject.Inject


class AccountRepositoryImpl @Inject constructor(var db: AppDatabase) : AccountRepository {

    override fun getAccounts(): Flowable<List<Account>> =
            db.accountDao.getAll()

    override fun addAccount(account: Account): Completable =
            Completable.fromAction { db.accountDao.insert(account) }.subscribeOn(Schedulers.io())

    override fun getAccount(id: Long): Flowable<Account> =
            db.accountDao.getById(id)

    override fun renameAccount(accountId: Long, title: String): Completable =
            Completable.fromAction { db.accountDao.updateTitle(title, accountId) }
                    .subscribeOn(Schedulers.io())

    override fun updateAccount(account: Account): Completable =
            Completable.fromAction { db.accountDao.update(account) }.subscribeOn(Schedulers.io())

}