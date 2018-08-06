package yandexschool.dmpolyakov.money.ui.tracker

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import yandexschool.dmpolyakov.money.Currency
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpView
import java.math.BigDecimal

@StateStrategyType(AddToEndSingleStrategy::class)
interface TrackerView : BaseMvpView {
    fun showBalance(count: BigDecimal, currency: Currency = Currency.Rubble)
    fun showAccounts(accounts: List<Account>)
    fun showDialog()
    fun showFinanceOperationDialog(financeOperation: FinanceOperation)
    fun hideAddAccountDialog()
    fun hideFinanceOperationDialog()
}