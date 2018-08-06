package yandexschool.dmpolyakov.money.ui.tracker.account.periodicoperations

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpView

@StateStrategyType(AddToEndSingleStrategy::class)
interface PeriodicOperationsView: BaseMvpView {
    fun showOperations(operations: List<FinanceOperation>)
    fun loadAccount()
}