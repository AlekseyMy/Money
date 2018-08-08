package yandexschool.dmpolyakov.money.ui.statistics

import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.repository.FinanceOperationRepository
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpPresenter
import javax.inject.Inject

class StatisticsPresenter @Inject constructor(
        val router: MainRouter,
        private val financeOperationRep: FinanceOperationRepository):
        BaseMvpPresenter<StatisticsView>(router) {

    override fun getScreenTag(): String = "Statistics presenter"

}