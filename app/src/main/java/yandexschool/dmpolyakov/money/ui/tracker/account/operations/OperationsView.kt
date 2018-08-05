package yandexschool.dmpolyakov.money.ui.tracker.account.operations

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpView

@StateStrategyType(AddToEndSingleStrategy::class)
interface OperationsView : BaseMvpView {
    fun showOperations(operations: List<FinanceOperation>)
    fun loadAccount()
}