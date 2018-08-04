package yandexschool.dmpolyakov.money.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import yandexschool.dmpolyakov.money.FinanceOperationState
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.models.FinanceOperation

interface FinanceOperationRepository {
    fun getFinanceOperations(accountId: Long): Flowable<List<FinanceOperation>>
    fun addFinanceOperationAndUpdateAccount(account: Account, operation: FinanceOperation): Completable
    fun addPeriodicFinanceOperation(operation: FinanceOperation): Completable
    fun getPeriodicFinanceOperations(timeNow: Long, state: String): Flowable<List<FinanceOperation>>
    fun getFinanceOperationsByIdAndInState(accountId: Long,
                                           state: String = FinanceOperationState.Done.name): Flowable<List<FinanceOperation>>
    fun updateFinanceOperation(financeOperation: FinanceOperation): Completable
}