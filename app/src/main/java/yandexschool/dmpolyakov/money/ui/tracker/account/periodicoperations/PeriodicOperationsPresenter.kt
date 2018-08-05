package yandexschool.dmpolyakov.money.ui.tracker.account.periodicoperations

import com.arellomobile.mvp.InjectViewState
import yandexschool.dmpolyakov.money.FinanceOperationState
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.repository.FinanceOperationRepository
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpPresenter
import javax.inject.Inject

@InjectViewState
class PeriodicOperationsPresenter @Inject constructor(
        router: MainRouter,
        private val financeOperationRep: FinanceOperationRepository): BaseMvpPresenter<PeriodicOperationsView>(router) {

    override fun getScreenTag(): String = "PeriodicOperationsPresenter"

    lateinit var account: Account

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.loadAccount()
    }

    fun loadAccount(account: Account) {
        this.account = account
        bind(onUi(financeOperationRep
                .getPeriodicFinanceOperations(account.id()!!,
                        FinanceOperationState.InProgress.name))
                .subscribe({
                    viewState.showOperations(it ?: listOf())
                }, {
                    viewState.showError(it)
                })
        )
    }
}