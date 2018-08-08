package yandexschool.dmpolyakov.money.di.features

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.repository.CurrencyRepositoryImpl
import yandexschool.dmpolyakov.money.ui.MainPresenter
import yandexschool.dmpolyakov.money.ui.about.AboutFragment
import yandexschool.dmpolyakov.money.ui.settings.SettingsFragment
import yandexschool.dmpolyakov.money.ui.statistics.StatisticsFragment
import yandexschool.dmpolyakov.money.ui.tracker.TrackerFragment
import yandexschool.dmpolyakov.money.ui.tracker.account.AccountFragment
import yandexschool.dmpolyakov.money.ui.tracker.account.operations.OperationsFragment
import yandexschool.dmpolyakov.money.ui.tracker.account.periodicoperations.PeriodicOperationsFragment
import yandexschool.dmpolyakov.money.ui.tracker.account.settings.AccountSettingsFragment


@Module
abstract class MainModule {

    @ContributesAndroidInjector(modules = [TrackerModule::class])
    abstract fun contributeTrackerFragment(): TrackerFragment

    @ContributesAndroidInjector(modules = [SettingsModule::class])
    abstract fun contributeSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector(modules = [AboutModule::class])
    abstract fun contributeAboutFragment(): AboutFragment

    @ContributesAndroidInjector(modules = [AccountModule::class])
    abstract fun contributeAccountFragment(): AccountFragment

    @ContributesAndroidInjector(modules = [AccountModule::class])
    abstract fun contributeOperationsFragment(): OperationsFragment

    @ContributesAndroidInjector(modules = [AccountModule::class])
    abstract fun contributeAccountSettingsFragment(): AccountSettingsFragment

    @ContributesAndroidInjector(modules = [AccountModule::class])
    abstract fun contributePeriodicOperationsFragment(): PeriodicOperationsFragment

    @ContributesAndroidInjector(modules = [StatisticsModule::class])
    abstract fun contributeStatisticsFragment(): StatisticsFragment

    @Module
    companion object {

        @Provides
        fun provideMainPresenter(router: MainRouter, currencyRepository: CurrencyRepositoryImpl) =
                MainPresenter(router, currencyRepository)

    }
}
