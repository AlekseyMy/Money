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
        private val accountRep: AccountRepository,
        private val financeOperationRep: FinanceOperationRepository) : BaseMvpPresenter<AccountView>(router) {

    private var accountId = 0L

    override fun onFirstViewAttach() {
        initAccount(accountId)
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
        bind(onUi(financeOperationRep.getFinanceOperations(accountId))
                .map {
                    account.amount = BigDecimal("0")
                    it.forEach { account.amount += it.amount }
                }
                .subscribe({
                    syncAccount(account)
                    viewState.showTitle(account.title)
                    viewState.showBalance(account.balance)
                    viewState.showTabs(account)
                }, {
                    viewState.showError(it)
                })
        )
    }

    private fun testB() {
        println()
    }

    private fun syncAccount(account: Account) {
        bind(onUi(accountRep.updateAccount(account))
                .subscribe({}, {
                    viewState.showError(it)
                })
        )
    }

    override fun getScreenTag() = "AccountPresenter"
}