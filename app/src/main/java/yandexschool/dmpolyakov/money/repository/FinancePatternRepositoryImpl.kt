package yandexschool.dmpolyakov.money.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import yandexschool.dmpolyakov.money.models.FinanceOperationPattern
import yandexschool.dmpolyakov.money.storage.AppDatabase
import javax.inject.Inject


class FinancePatternRepositoryImpl @Inject constructor(var db: AppDatabase): FinancePatternRepository {
    override fun getAll(): Flowable<List<FinanceOperationPattern>> = db.financeOperationPatternDao.getAll()

    override fun update(financeOperationPattern: FinanceOperationPattern): Completable =
            Completable.fromAction { db.financeOperationPatternDao.update(financeOperationPattern) }
                    .subscribeOn(Schedulers.io())

    override fun delete(financeOperationPattern: FinanceOperationPattern): Completable =
            Completable.fromAction { db.financeOperationPatternDao.delete(financeOperationPattern) }
                    .subscribeOn(Schedulers.io())

    override fun save(financeOperationPattern: FinanceOperationPattern): Completable =
            Completable.fromAction { db.financeOperationPatternDao.insert(financeOperationPattern) }
                    .subscribeOn(Schedulers.io())
}