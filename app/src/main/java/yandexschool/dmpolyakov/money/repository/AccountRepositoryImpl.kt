package yandexschool.dmpolyakov.money.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import yandexschool.dmpolyakov.money.Currency
import yandexschool.dmpolyakov.money.OperationCategory
import yandexschool.dmpolyakov.money.OperationType
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.models.FinanceOperation


class AccountRepositoryImpl : AccountRepository {

    private val fakeAccounts = ArrayList<Account>()
    override val subjectFakeAccounts: Subject<List<Account>> = BehaviorSubject.create<List<Account>>()

    init {
        createFakeAccounts()
    }

    override fun getAccounts(): Single<ArrayList<Account>> =
            Single.fromObservable(Observable.fromArray(fakeAccounts))

    override fun addAccount(account: Account): Completable {
        fakeAccounts.add(account.copy(id = (fakeAccounts.size + 1).toLong()))
        subjectFakeAccounts.onNext(fakeAccounts)

        fakeAccounts.sortWith(Comparator { a, b -> if (a.balance > b.balance) 0 else 1 })
        return Completable.complete()
    }

    override fun getAccount(id: Long): Single<Account> {
        return Single.just(fakeAccounts.find { it.id() == id }
                ?: throw Exception("Account not found"))
    }

    override fun addFinanceOperation(accountId: Long, operation: FinanceOperation): Completable {
        val account = fakeAccounts.find { it.id() == accountId }
                ?: throw Exception("Account not found")

        account.addFinanceOperation(operation)
        subjectFakeAccounts.onNext(fakeAccounts)
        return Completable.complete()
    }

    override fun renameAccount(accountId: Long, title: String): Completable {
        val account = fakeAccounts.find { it.id() == accountId }
                ?: throw Exception("Account not found")

        account.title = title
        subjectFakeAccounts.onNext(fakeAccounts)
        return Completable.complete()
    }

    private fun createFakeAccounts() {

        val operations = ArrayList<FinanceOperation>()
        operations.add(FinanceOperation(
                "Магазин у Петра", 750.toBigDecimal(), OperationType.Expense, OperationCategory.Products, Currency.Rubble, "25.07.2018", 1L, 1L))
        operations.add(FinanceOperation(
                "Аренда ламзаков", 20000.toBigDecimal(), OperationType.Expense, OperationCategory.Other, Currency.Rubble, "26.07.2018", 1L, 2L))
        operations.add(FinanceOperation(
                "Доход с аренды ламзаков", 35000.toBigDecimal(), OperationType.Income, OperationCategory.Salary, Currency.Rubble, "27.07.2018", 1L, 3L))

        fakeAccounts.add(Account("Рабочий", 20700.toBigDecimal(), Currency.Rubble, ArrayList(operations), 1L))
        fakeAccounts.add(Account("Копилочка", 256000.toBigDecimal(), Currency.Rubble, ArrayList(operations), 2L))
        fakeAccounts.add(Account("Заграничный", 500.toBigDecimal(), Currency.Dollar, ArrayList(operations), 3L))
    }

}