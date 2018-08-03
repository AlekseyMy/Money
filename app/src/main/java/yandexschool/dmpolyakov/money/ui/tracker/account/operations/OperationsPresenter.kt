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
        val accountRep: AccountRepository,
        val financeOperationRep: FinanceOperationRepository) : BaseMvpPresenter<OperationsView>(router) {

    override fun getScreenTag() = "OperationsPresenter"

    private lateinit var account: Account

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.loadAccount()
    }

    fun addOperation(operation: FinanceOperation) {
        bind((financeOperationRep.addFinanceOperation(account.id(), operation)
                .subscribe({
                    updateOperations()
                }, {
                    viewState.showError(it)
                }))
        )
    }

    private fun updateOperations() {
        bind(onUi(financeOperationRep.getFinanceOperations(account.id()))
                .subscribe({
                    viewState.showOperations(it)
                }, {
                    viewState.showError(it)
                })
        )
    }

    fun loadAccount(account: Account) {
        this.account = account
        bind(onUi(financeOperationRep.getFinanceOperations(account.id()))
                .subscribe({
                    viewState.showOperations(it)
                }, {
                    viewState.showError(it)
                })
        )
//        viewState.showOperations(account.operations)
    }

}