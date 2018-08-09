package yandexschool.dmpolyakov.money.ui.tracker.account.periodicoperations

import com.arellomobile.mvp.InjectViewState
import yandexschool.dmpolyakov.money.FinanceOperationState
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.repository.FinanceOperationRepository
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpPresenter
import javax.inject.Inject

@InjectViewState
class PeriodicOperationsPresenter @Inject constructor(
        router: MainRouter,
        private val financeOperationRep: FinanceOperationRepository) : BaseMvpPresenter<PeriodicOperationsView>(router) {

    override fun getScreenTag(): String = "PeriodicOperationsPresenter"

    lateinit var account: Account

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.loadAccount()
    }

    override fun attachView(view: PeriodicOperationsView?) {
        super.attachView(view)
        updatePeriodicOperations()
    }

    private fun updatePeriodicOperations() {
        bind(onUi(financeOperationRep
                .getFinanceOperationsByIdAndInState(account.id()!!,
                        FinanceOperationState.InProgress.name))
                .subscribe({
                    viewState.showOperations(it ?: listOf())
                }, {
                    viewState.showError(it)
                })
        )
    }

    fun loadAccount(account: Account) {
        this.account = account
        updatePeriodicOperations()
    }

    fun onDeleteClick(financeOperation: FinanceOperation) {
        financeOperation.state = FinanceOperationState.Canceled
        bind(onUi(financeOperationRep.updateFinanceOperation(financeOperation))
                .subscribe({
                }, {
                    viewState.showError(it)
                }))
    }
}