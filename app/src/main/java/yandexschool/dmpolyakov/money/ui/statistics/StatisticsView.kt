package yandexschool.dmpolyakov.money.ui.statistics

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpView

@StateStrategyType(AddToEndSingleStrategy::class)
interface StatisticsView : BaseMvpView {
    fun setChartData(operations: List<FinanceOperation>)
    fun setTransactionDetails(titleId: Int?, img: Int?, amount: String)
    fun setTotal(value: String)
}