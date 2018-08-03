package yandexschool.dmpolyakov.money.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.storage.AppDatabase
import javax.inject.Inject

class FinanceOperationRepositoryImpl @Inject constructor(var db: AppDatabase): FinanceOperationRepository {

    override fun getFinanceOperations(accountId: Long): Flowable<List<FinanceOperation>> =
            db.financeOperationDao.getByAccountId(accountId)

    override fun addFinanceOperation(accountId: Long, operation: FinanceOperation): Completable {
        operation.accountKey = accountId
        return Completable.fromAction {
            db.accountFinanceOperationDao
                    .insertFinanceOperationAndUpdateAccount(operation, accountId, operation.amount)
//            db.financeOperationDao.insert(operation)
        }.subscribeOn(Schedulers.io())
    }

}