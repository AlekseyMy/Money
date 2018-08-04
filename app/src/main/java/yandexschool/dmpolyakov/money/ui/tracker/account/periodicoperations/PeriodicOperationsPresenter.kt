package yandexschool.dmpolyakov.money.ui.tracker.account.periodicoperations

import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.repository.FinanceOperationRepository
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpPresenter
import javax.inject.Inject

class PeriodicOperationsPresenter /*@Inject constructor(
        router: MainRouter,
        private val financeOperationRep: FinanceOperationRepository)*/: BaseMvpPresenter<PeriodicOperationsView>(MainRouter()) {

    override fun getScreenTag(): String = "PeriodicOperationsPresenter"
}