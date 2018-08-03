package yandexschool.dmpolyakov.money.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import yandexschool.dmpolyakov.money.models.FinanceOperation

interface FinanceOperationRepository {
    fun getFinanceOperations(accountId: Long): Flowable<List<FinanceOperation>>
    fun addFinanceOperation(accountId: Long, operation: FinanceOperation): Completable
}