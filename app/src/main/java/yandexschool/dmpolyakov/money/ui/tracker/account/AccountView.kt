package yandexschool.dmpolyakov.money.ui.tracker.account

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpView

@StateStrategyType(AddToEndSingleStrategy::class)
interface AccountView : BaseMvpView {

    fun showTitle(title: String)
    fun showBalance(balance: String)
    fun showTabs(account: Account)
    fun loadAccountId()

}