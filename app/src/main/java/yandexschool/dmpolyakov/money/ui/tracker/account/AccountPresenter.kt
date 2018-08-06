package yandexschool.dmpolyakov.money.ui.tracker.account

import com.arellomobile.mvp.InjectViewState
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.repository.AccountRepository
import yandexschool.dmpolyakov.money.repository.FinanceOperationRepository
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpPresenter
import java.math.BigDecimal
import javax.inject.Inject


@InjectViewState
class AccountPresenter @Inject constructor(
        val router: MainRouter,
        private val accountRep: AccountRepository) : BaseMvpPresenter<AccountView>(router) {

    private var accountId = 0L

    override fun onFirstViewAttach() {
        viewState.loadAccountId()
    }

    fun initAccount(id: Long) {
        accountId = id
        bind(onUi(accountRep.getAccount(id)).subscribe({
            updateAccount(it)
        }, {
            viewState?.showError(it)
        })
        )
    }

    private fun updateAccount(account: Account) {
        viewState.showTitle(account.title)
        viewState.showBalance(account.balance)
        viewState.showTabs(account)
    }

    override fun getScreenTag() = "AccountPresenter"
}