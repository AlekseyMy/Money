package yandexschool.dmpolyakov.money.ui.statistics

import com.arellomobile.mvp.InjectViewState
import yandexschool.dmpolyakov.money.OperationCategory
import yandexschool.dmpolyakov.money.R
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.repository.FinanceOperationRepository
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpPresenter
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
            amount = amount.plus(item.amount)  // делать в другом потоке
        }
        viewState.setTotal(amount.toString())
    }

    fun computeCategoryStatistics(title: String) {
        var amount = BigDecimal.valueOf(0.0)
        val category = OperationCategory.valueOf(title)
        for (item in operations) {
            if (item.category == category) {
                amount = amount.plus(item.amount)
            }
        }
        viewState.setTransactionDetails(title, category.icon, amount.toString())
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