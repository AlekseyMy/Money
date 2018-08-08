package yandexschool.dmpolyakov.money.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import yandexschool.dmpolyakov.money.R
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpFragment
import javax.inject.Inject

class StatisticsFragment: BaseMvpFragment<StatisticsPresenter>(), StatisticsView {

    companion object {
        val instance = StatisticsFragment()
    }

    @Inject
    lateinit var router: MainRouter

    @Inject
    @InjectPresenter
    lateinit var presenter: StatisticsPresenter

    @ProvidePresenter
    override fun providePresenter(): StatisticsPresenter = presenter

    override fun getLogName(): String = "Statistics fragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_statistics, container, false)
}