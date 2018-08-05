package yandexschool.dmpolyakov.money.ui.tracker.account.periodicoperations

import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpView

interface PeriodicOperationsView: BaseMvpView {
    fun showOperations(operations: List<FinanceOperation>)
    fun loadAccount()
}