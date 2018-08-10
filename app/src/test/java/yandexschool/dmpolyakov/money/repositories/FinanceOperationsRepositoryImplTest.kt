package yandexschool.dmpolyakov.money.repositories

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import yandexschool.dmpolyakov.money.Base.BaseUnitTest
import yandexschool.dmpolyakov.money.Currency
import yandexschool.dmpolyakov.money.FinanceOperationState
import yandexschool.dmpolyakov.money.OperationCategory
import yandexschool.dmpolyakov.money.OperationType
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.repository.FinanceOperationRepository
import yandexschool.dmpolyakov.money.repository.FinanceOperationRepositoryImpl
import yandexschool.dmpolyakov.money.storage.AppDatabase
import yandexschool.dmpolyakov.money.storage.FinanceOperationDao
import java.util.concurrent.TimeUnit

class FinanceOperationsRepositoryImplTest: BaseUnitTest() {

    lateinit var financeOperationRep: FinanceOperationRepository

    @Mock
    lateinit var db: AppDatabase

    @Mock
    lateinit var financeOperationDao: FinanceOperationDao

    private val finOpList = listOf(
            FinanceOperation("", 150.toBigDecimal(), OperationType.Income, OperationCategory.Salary, Currency.Rubble, "", 1L, 1L, FinanceOperationState.InProgress, 1L, 1L),
            FinanceOperation("", 2.toBigDecimal(), OperationType.Income, OperationCategory.Salary, Currency.Dollar, "", 2L, 2L, FinanceOperationState.Done, 1L, 1L)
    )

    @Before
    override fun onInit() {
        super.onInit()

        financeOperationRep = FinanceOperationRepositoryImpl(db)
    }

    override fun onMockInit() {

    }

    @Test
    fun getFinOp_success() {
        Mockito.doReturn(financeOperationDao).`when`(db).financeOperationDao
        Mockito.doReturn(Flowable.just(finOpList)).`when`(financeOperationDao).getByAccountId(1L)

        financeOperationRep.getFinanceOperations(1L)
                .subscribeOn(Schedulers.newThread())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it == listOf(
                            FinanceOperation("", 150.toBigDecimal(), OperationType.Income, OperationCategory.Salary, Currency.Rubble, "", 1L, 1L, FinanceOperationState.InProgress, 1L, 1L),
                            FinanceOperation("", 2.toBigDecimal(), OperationType.Income, OperationCategory.Salary, Currency.Dollar, "", 2L, 2L, FinanceOperationState.Done, 1L, 1L)
                    )
                }
    }

    @Test
    fun getPeriodicFinOp_success() {
        Mockito.doReturn(financeOperationDao).`when`(db).financeOperationDao
        Mockito.doReturn(Flowable.just(listOf(finOpList[0]))).`when`(financeOperationDao)
                .getPeriodicFinanceOperations(1L, FinanceOperationState.InProgress.name)

        financeOperationRep.getPeriodicFinanceOperations(1L, FinanceOperationState.InProgress.name)
                .subscribeOn(Schedulers.newThread())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it == listOf(
                            FinanceOperation("", 150.toBigDecimal(), OperationType.Income, OperationCategory.Salary, Currency.Rubble, "", 1L, 1L, FinanceOperationState.InProgress, 1L, 1L)
                            )
                }
    }
}