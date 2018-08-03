package yandexschool.dmpolyakov.money.ui.tracker.account.operations

import com.arellomobile.mvp.InjectViewState
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.repository.AccountRepository
import yandexschool.dmpolyakov.money.repository.FinanceOperationRepository
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpPresenter
import javax.inject.Inject


@InjectViewState
class OperationsPresenter @Inject constructor(
        router: MainRouter,
        private val financeOperationRep: FinanceOperationRepository) : BaseMvpPresenter<OperationsView>(router) {

    override fun getScreenTag() = "OperationsPresenter"

    private var accountId: Long = 0

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.loadAccount()
    }

    fun addOperation(operation: FinanceOperation) {
        bind((financeOperationRep.addFinanceOperation(accountId, operation)
                .subscribe({
                    updateOperations()
                }, {
                    viewState.showError(it)
                }))
        )
    }

    private fun updateOperations() {
        bind(onUi(financeOperationRep.getFinanceOperations(accountId))
                .subscribe({
                    viewState.showOperations(it ?: listOf())
                }, {
                    viewState.showError(it)
                })
        )
    }

    fun loadAccount(accountId: Long) {
        this.accountId = accountId
        bind(onUi(financeOperationRep.getFinanceOperations(accountId))
                .subscribe({
                    viewState.showOperations(it ?: listOf())
                }, {
                    viewState.showError(it)
                })
        )
    }

}