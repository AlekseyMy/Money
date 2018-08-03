package yandexschool.dmpolyakov.money.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import yandexschool.dmpolyakov.money.Currency
import yandexschool.dmpolyakov.money.OperationCategory
import yandexschool.dmpolyakov.money.OperationType
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.storage.AppDatabase
import javax.inject.Inject


class AccountRepositoryImpl @Inject constructor(var db: AppDatabase): AccountRepository {

    private val fakeAccounts = ArrayList<Account>()
    override val subjectFakeAccounts: Subject<List<Account>> = BehaviorSubject.create<List<Account>>()

    override fun getAccounts(): Flowable<List<Account>> =
            db.accountDao.getAll()

    override fun addAccount(account: Account): Completable =
            Completable.fromAction { db.accountDao.insert(account) }.subscribeOn(Schedulers.io())

    override fun getAccount(id: Long): Flowable<Account> =
            db.accountDao.getById(id)

    override fun renameAccount(accountId: Long, title: String): Completable {
        val account = fakeAccounts.find { it.id() == accountId }
                ?: throw Exception("Account not found")

        account.title = title
        subjectFakeAccounts.onNext(fakeAccounts)
        return Completable.complete()
    }

}