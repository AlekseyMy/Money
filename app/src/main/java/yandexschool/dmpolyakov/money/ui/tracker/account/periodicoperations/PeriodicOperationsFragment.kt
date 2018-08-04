package yandexschool.dmpolyakov.money.ui.tracker.account.periodicoperations

import com.arellomobile.mvp.presenter.InjectPresenter
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpFragment
import yandexschool.dmpolyakov.money.ui.tracker.account.operations.OperationsPresenter
import javax.inject.Inject

class PeriodicOperationsFragment: BaseMvpFragment<PeriodicOperationsPresenter>(),
        PeriodicOperationsView {

//    @Inject
//    lateinit var router: MainRouter
//
//    @Inject
//    @InjectPresenter
//    lateinit var presenter: OperationsPresenter

    override fun providePresenter(): PeriodicOperationsPresenter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLogName(): String = "PeriodicOperationsFragment"

}