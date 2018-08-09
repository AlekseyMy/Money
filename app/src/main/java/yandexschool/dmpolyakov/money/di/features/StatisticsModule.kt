package yandexschool.dmpolyakov.money.di.features

import dagger.Module
import dagger.Provides
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.repository.FinanceOperationRepository
import yandexschool.dmpolyakov.money.ui.statistics.StatisticsPresenter

@Module
object StatisticsModule {

    @JvmStatic
    @Provides
    fun provideStatisticsPresenter(router: MainRouter,
                                   financeOperationRep: FinanceOperationRepository) =
            StatisticsPresenter(router, financeOperationRep)

}