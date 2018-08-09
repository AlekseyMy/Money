package yandexschool.dmpolyakov.money.di.features

import dagger.Module
import dagger.Provides
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.repository.AccountRepository
import yandexschool.dmpolyakov.money.repository.FinanceOperationRepository
import yandexschool.dmpolyakov.money.ui.tracker.account.AccountPresenter
import yandexschool.dmpolyakov.money.ui.tracker.account.operations.OperationsPresenter
import yandexschool.dmpolyakov.money.ui.tracker.account.periodicoperations.PeriodicOperationsPresenter
import yandexschool.dmpolyakov.money.ui.tracker.account.settings.AccountSettingsPresenter


@Module
object AccountModule {

    @JvmStatic
    @Provides
    fun provideAccountPresenter(router: MainRouter, accountRep: AccountRepository) =
            AccountPresenter(router, accountRep)

    @JvmStatic
    @Provides
    fun provideOperationsPresenter(router: MainRouter,
                                   financeOperationRep: FinanceOperationRepository) =
            OperationsPresenter(router, financeOperationRep)

    @JvmStatic
    @Provides
    fun provideAccountSettingsPresenter(router: MainRouter, accountRep: AccountRepository) =
            AccountSettingsPresenter(router, accountRep)

    @JvmStatic
    @Provides
    fun providePeriodicOperationPresenter(router: MainRouter,
                                          financeOperationRep: FinanceOperationRepository) =
            PeriodicOperationsPresenter(router, financeOperationRep)

}