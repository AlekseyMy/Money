package yandexschool.dmpolyakov.money.ui.tracker.account.financepatterns

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
class FinancePatternsPresenter @Inject constructor(
        router: MainRouter,
        private val financePatternRep: FinancePatternRepository,
        private val financeOperationRep: FinanceOperationRepository) : BaseMvpPresenter<FinancePatternsView>(router) {

    private lateinit var account: Account

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.loadAccount()
    }

    fun setAccount(account: Account) {
        this.account = account
    }

    override fun attachView(view: FinancePatternsView?) {
        super.attachView(view)
        loadPatterns()
    }

    private fun loadPatterns() {
        bind(onUi(financePatternRep.getAll())
                .subscribe({
                    viewState.showOperations(it)
                }, {
                    viewState.showError(it)
                }))
    }

    fun deletePattern(pattern: FinanceOperationPattern) {
        bind(onUi(financePatternRep.delete(pattern))
                .subscribe({

                }, {
                    viewState.showError(it)
                }))
    }

    fun addOperation(financeOperation: FinanceOperation) {
        financeOperation.accountKey = account.id()!!
        bind(onUi(financeOperationRep.addFinanceOperationAndUpdateAccount(account ,financeOperation))
                .subscribe({

                }, {
                    viewState.showError(it)
                }))
    }

    override fun getScreenTag(): String = "FinancePatternsPresenter"

}