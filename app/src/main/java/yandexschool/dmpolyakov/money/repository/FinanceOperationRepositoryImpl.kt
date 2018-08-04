package yandexschool.dmpolyakov.money.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import yandexschool.dmpolyakov.money.Currency
import yandexschool.dmpolyakov.money.OperationType
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.storage.AppDatabase
import yandexschool.dmpolyakov.money.utils.toDollars
import yandexschool.dmpolyakov.money.utils.toRubbles
import java.math.BigDecimal
import javax.inject.Inject

class FinanceOperationRepositoryImpl @Inject constructor(var db: AppDatabase): FinanceOperationRepository {

    override fun getFinanceOperations(accountId: Long): Flowable<List<FinanceOperation>> =
            db.financeOperationDao.getByAccountId(accountId)

    override fun addFinanceOperationAndUpdateAccount(account: Account,
                                                     operation: FinanceOperation): Completable {
        operation.accountKey = account.id()!!
        return Completable.fromAction {
            db.accountFinanceOperationDao
                    .insertFinanceOperationAndUpdateAccount(operation,
                            account.id()!!, when(account.currency) {
                        Currency.Rubble -> operation.amount.toRubbles(operation.currency)
                        Currency.Dollar -> operation.amount.toDollars(operation.currency)
                    }.multiply(
                            if (operation.type == OperationType.Income)
                                BigDecimal("1")
                            else
                                BigDecimal("-1")))
        }.subscribeOn(Schedulers.io())
    }

    override fun addPeriodicFinanceOperation(operation: FinanceOperation): Completable = Completable.fromAction {
            db.financeOperationDao.insert(operation)
        }.subscribeOn(Schedulers.io())

    override fun getPeriodicFinanceOperations(timeNow: Long, state: Int): Flowable<List<FinanceOperation>> =
            db.financeOperationDao.getPeriodicFinanceOperations(timeNow, state)

    override fun getFinanceOperationsByIdAndInState(accountId: Long,
                                                    state: Int): Flowable<List<FinanceOperation>> =
            db.financeOperationDao.getFinanceOperationsByIdAndInState(accountId, state)

    override fun updateFinanceOperation(financeOperation: FinanceOperation): Completable =
            Completable.fromAction { db.financeOperationDao.update(financeOperation) }.subscribeOn(Schedulers.io())

}