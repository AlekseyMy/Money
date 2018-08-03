package yandexschool.dmpolyakov.money.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import yandexschool.dmpolyakov.money.Currency
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.storage.AppDatabase
import yandexschool.dmpolyakov.money.utils.toDollars
import yandexschool.dmpolyakov.money.utils.toRubbles
import javax.inject.Inject

class FinanceOperationRepositoryImpl @Inject constructor(var db: AppDatabase): FinanceOperationRepository {

    override fun getFinanceOperations(accountId: Long): Flowable<List<FinanceOperation>> =
            db.financeOperationDao.getByAccountId(accountId)

    override fun addFinanceOperation(account: Account, operation: FinanceOperation): Completable {
        operation.accountKey = account.id()
        return Completable.fromAction {
            db.accountFinanceOperationDao
                    .insertFinanceOperationAndUpdateAccount(operation,
                            account.id(), when(account.currency) {
                        Currency.Rubble -> operation.amount.toRubbles(operation.currency)
                        Currency.Dollar -> operation.amount.toDollars(operation.currency)
                    })
        }.subscribeOn(Schedulers.io())
    }

}