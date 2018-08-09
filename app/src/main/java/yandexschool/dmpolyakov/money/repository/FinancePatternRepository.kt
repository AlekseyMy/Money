package yandexschool.dmpolyakov.money.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import yandexschool.dmpolyakov.money.models.FinanceOperationPattern

interface FinancePatternRepository {
    fun getAll(): Flowable<List<FinanceOperationPattern>>
    fun update(financeOperationPattern: FinanceOperationPattern): Completable
    fun delete(financeOperationPattern: FinanceOperationPattern): Completable
    fun save(financeOperationPattern: FinanceOperationPattern): Completable
}