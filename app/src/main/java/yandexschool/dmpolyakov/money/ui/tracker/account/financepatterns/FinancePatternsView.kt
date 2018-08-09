package yandexschool.dmpolyakov.money.ui.tracker.account.financepatterns

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpView

@StateStrategyType(AddToEndSingleStrategy::class)
interface FinancePatternsView : BaseMvpView {
}