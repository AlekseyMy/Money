package yandexschool.dmpolyakov.money.ui

import android.os.Build
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import yandexschool.dmpolyakov.money.Base.BaseUnitTest
import yandexschool.dmpolyakov.money.Base.any
import yandexschool.dmpolyakov.money.Currency
import yandexschool.dmpolyakov.money.FinanceOperationState
import yandexschool.dmpolyakov.money.OperationCategory
import yandexschool.dmpolyakov.money.OperationType
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.repository.*
import yandexschool.dmpolyakov.money.storage.AppDatabase
import yandexschool.dmpolyakov.money.ui.tracker.TrackerPresenter
import yandexschool.dmpolyakov.money.ui.tracker.TrackerView
import java.math.BigDecimal

//@RunWith(RobolectricTestRunner::class)
//@Config(sdk = [Build.VERSION_CODES.O_MR1])
class TrackerPresenterUnitTest: BaseUnitTest() {

    lateinit var presenter: TrackerPresenter

    @Mock
    lateinit var view: TrackerView

    @Mock
    lateinit var router: MainRouter

    @Mock
    lateinit var accountRep: AccountRepository

    @Mock
    lateinit var financeOperationRep: FinanceOperationRepository

    private val periodicFinOpList = listOf(
            FinanceOperation("", 150.toBigDecimal(), OperationType.Income, OperationCategory.Salary, Currency.Rubble, "", 1L, 1L, FinanceOperationState.InProgress, 0L, 1L),
            FinanceOperation("", 2.toBigDecimal(), OperationType.Income, OperationCategory.Salary, Currency.Dollar, "", 2L, 2L, FinanceOperationState.InProgress, 0L, 1L)
            )

    private val accountsList = listOf(
            Account(id = 0L, currency = Currency.Rubble, amount = BigDecimal("0.0"), title = "title1"),
            Account(id = 1L, currency = Currency.Rubble, amount = BigDecimal("0.0"), title = "title2")
    )

//    @Before
//    override fun onInit() {
//        super.onInit()
//
//        presenter = TrackerPresenter(router, accountRep, financeOperationRep)
//    }

    override fun onMockInit() {
    }

//    @Test
//    fun addFinOp_success() {
//        doReturn(Flowable.just(accountsList)).`when`(accountRep).getAccounts()
//
//        doReturn(Flowable.just(periodicFinOpList))
//                .`when`(financeOperationRep)
//                .getPeriodicFinanceOperations(1L, FinanceOperationState.InProgress.name)
//
//        presenter.attachView(view)
//
//        Mockito.verify(view).showFinanceOperationDialog(any())
//    }
}