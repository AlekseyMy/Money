package yandexschool.dmpolyakov.money.ui.tracker

import com.arellomobile.mvp.InjectViewState
import yandexschool.dmpolyakov.money.FinanceOperationState
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.repository.AccountRepository
import yandexschool.dmpolyakov.money.repository.FinanceOperationRepository
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpPresenter
import java.util.*
import javax.inject.Inject


@InjectViewState
class TrackerPresenter @Inject constructor(
        val router: MainRouter,
        private val accountRep: AccountRepository,
        private val financeOperationRep: FinanceOperationRepository) : BaseMvpPresenter<TrackerView>(router) {

    private val periodicFinanceOperations: Queue<FinanceOperation> = ArrayDeque<FinanceOperation>()

    override fun getScreenTag(): String {
        return "TrackerPresenter"
    }

    override fun attachView(view: TrackerView?) {
        super.attachView(view)
        updateAccounts()
        getPeriodicTransactions()
    }

    fun showNext() {
        if (!periodicFinanceOperations.isEmpty()) {
            viewState.showFinanceOperationDialog(periodicFinanceOperations.poll())
        }
    }

    private fun getPeriodicTransactions() {
        bind(onUi(financeOperationRep
                .getPeriodicFinanceOperations(System.currentTimeMillis(),
                        FinanceOperationState.InProgress.code))
                .subscribe({
                    periodicFinanceOperations.addAll(it)
                    showNext()
                }, {
                    viewState.showError(it)
                })
        )
    }

    fun addAccount(account: Account) {
        bind(accountRep.addAccount(account).subscribe({
            updateAccounts()
        }, {
            viewState.showError(it)
        }))
    }

    fun onAccountClick(account: Account) {
        router.showAccountScreen(account.id()!!)
    }

    private fun updateAccounts() {
        bind(onUi(accountRep.getAccounts()).subscribe({
            viewState.showAccounts(it)
        }, {
            viewState.showError(it)
        }))
    }

    fun addOperationWithAssociatedAccount(operation: FinanceOperation) {
        bind(onUi(accountRep.getAccount(operation.accountKey))
                .subscribe({
                    addOperationAndUpdateDelayed(it, operation)
                }, {
                    viewState.showError(it)
                }))
    }

    private fun addOperationAndUpdateDelayed(account: Account, operation: FinanceOperation) {
        operation.accountKey = account.id()!!
        if (operation.state == FinanceOperationState.InProgress) {
            bind(onUi(financeOperationRep.updateFinanceOperation(operation))
                    .subscribe({}, {
                        viewState.showError(it)
                    })
            )
        }

        bind((financeOperationRep.addFinanceOperationAndUpdateAccount(account,
                operation.copy(state = FinanceOperationState.Done, id = null))
                .subscribe({
                }, {
                    viewState.showError(it)
                }))
        )
    }

    fun updatePeriodicOperation(operation: FinanceOperation) {
        bind(onUi(financeOperationRep.updateFinanceOperation(operation))
                .subscribe({}, {
                    viewState.showError(it)
                })
        )
    }

}