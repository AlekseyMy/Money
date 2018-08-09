package yandexschool.dmpolyakov.money.ui.tracker.account.financepatterns

import com.arellomobile.mvp.InjectViewState
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.repository.FinanceOperationRepository
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpPresenter
import javax.inject.Inject

@InjectViewState
class FinancePatternsPresenter @Inject constructor(
        router: MainRouter,
        private val financeOperationRep: FinanceOperationRepository) : BaseMvpPresenter<FinancePatternsView>(router) {

    override fun getScreenTag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}