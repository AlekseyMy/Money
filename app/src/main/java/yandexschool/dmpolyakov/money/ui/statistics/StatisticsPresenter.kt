package yandexschool.dmpolyakov.money.ui.statistics

import com.arellomobile.mvp.InjectViewState
import yandexschool.dmpolyakov.money.Currency
import yandexschool.dmpolyakov.money.OperationCategory
import yandexschool.dmpolyakov.money.R
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.repository.FinanceOperationRepository
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpPresenter
import yandexschool.dmpolyakov.money.utils.toRubbles
import java.math.BigDecimal
import javax.inject.Inject

@InjectViewState
class StatisticsPresenter @Inject constructor(
        val router: MainRouter,
        private val financeOperationRep: FinanceOperationRepository) :
        BaseMvpPresenter<StatisticsView>(router) {

    private var since: Long? = null
    private var until: Long? = null
    private var operations = listOf<FinanceOperation>()

    fun setSince(time: Long) {
        since = time
    }

    fun setUntil(time: Long) {
        until = time
    }

    private fun updateTotal(operations: List<FinanceOperation>) {
        var amount = BigDecimal.valueOf(0.0)
        for (item in operations) {
            amount = amount.plus(item.amount.toRubbles(item.currency))  // делать в другом потоке
        }
        viewState.setTotal(amount.toString())
    }

    fun computeCategoryStatistics(titleId: Int) {
        var amount = BigDecimal.valueOf(0.0)
        var category: OperationCategory = OperationCategory.Other
        for (item in OperationCategory.values()) {
            if (titleId == item.title) {
                category = item
            }
        }
        for (item in operations) {
            if (item.category == category) {
                amount = amount.plus(item.amount.toRubbles(item.currency))
            }
        }
        viewState.setTransactionDetails(titleId, category.icon, "%.2f".format(amount), Currency.Rubble.sign)
    }

    fun getTransactionInPeriod() {
        if (since != null && until != null) {
            bind(onUi(financeOperationRep.getFinOpInPeriod(since!!, until!!))
                    .subscribe({
                        operations = it
                        viewState.setChartData(it)
                        updateTotal(it)
                    }, {
                        viewState.showError(it)
                    }))
        } else {
            viewState.showToast(R.string.enter_period)
        }
    }

    override fun getScreenTag(): String = "Statistics presenter"

}