package yandexschool.dmpolyakov.money.ui.tracker.account.operations

import com.arellomobile.mvp.InjectViewState
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.models.FinanceOperationPattern
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.repository.FinanceOperationRepository
import yandexschool.dmpolyakov.money.repository.FinancePatternRepository
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpPresenter
import javax.inject.Inject


@InjectViewState
class OperationsPresenter @Inject constructor(
        router: MainRouter,
        private val financeOperationRep: FinanceOperationRepository,
        private val financePatternRep: FinancePatternRepository) : BaseMvpPresenter<OperationsView>(router) {

    override fun getScreenTag() = "OperationsPresenter"

    private lateinit var account: Account

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.loadAccount()
    }

    fun addOperation(operation: FinanceOperation) {
        bind((financeOperationRep.addFinanceOperationAndUpdateAccount(account, operation)
                .subscribe({
                    updateOperations()
                }, {
                    viewState.showError(it)
                }))
        )
    }

    private fun updateOperations() {
        bind(onUi(financeOperationRep
                .getFinanceOperations(account.id()!!))
                .subscribe({
                    viewState.showOperations(it ?: listOf())
                }, {
                    viewState.showError(it)
                })
        )
    }

    fun loadAccount(account: Account) {
        this.account = account
        bind(onUi(financeOperationRep
                .getFinanceOperations(account.id()!!))
                .subscribe({
                    viewState.showOperations(it ?: listOf())
                }, {
                    viewState.showError(it)
                })
        )
    }

    fun createFinancePattern(financeOperation: FinanceOperation) {
        bind(onUi(financePatternRep.save(FinanceOperationPattern(
                title = financeOperation.title,
                amount = financeOperation.amount,
                category = financeOperation.category,
                type = financeOperation.type,
                currency = financeOperation.currency
        ))).subscribe({

        }, {
            viewState.showError(it)
        }))
    }

}