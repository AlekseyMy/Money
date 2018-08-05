package yandexschool.dmpolyakov.money.ui.about

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpView

@StateStrategyType(AddToEndSingleStrategy::class)
interface AboutView : BaseMvpView {
    fun showVersion(version: String)
}