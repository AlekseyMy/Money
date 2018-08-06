package yandexschool.dmpolyakov.money.androidjunit

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import yandexschool.dmpolyakov.money.Currency
import yandexschool.dmpolyakov.money.FinanceOperationState
import yandexschool.dmpolyakov.money.OperationCategory
import yandexschool.dmpolyakov.money.OperationType
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.storage.AppDatabase
import yandexschool.dmpolyakov.money.storage.FinanceOperationDao
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class FinanceOperationDaoInstrumentedTest {
    private lateinit var financeOperationDao: FinanceOperationDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getTargetContext()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java!!).build()
        financeOperationDao = appDatabase.financeOperationDao
        appDatabase.accountDao.insert(Account(id = 0L,
                currency = Currency.Rubble,
                amount = BigDecimal("0"),
                title = "titleAccount"))
    }

    fun fillHelper(range: LongRange) {
        for (index in range) {
            financeOperationDao.insert(FinanceOperation(id = index,
                    amount = BigDecimal("1000"),
                    currency = Currency.Rubble,
                    title = "title1",
                    date = "today",
                    category = OperationCategory.Education,
                    state = FinanceOperationState.Done,
                    accountKey = 0L,
                    timeStart = System.currentTimeMillis(),
                    timeFinish = System.currentTimeMillis() + 2000,
                    type = OperationType.Expense))
        }
    }

    @Test
    fun getAll() {
        fillHelper(0L..4L)
        financeOperationDao.getAll()
                .subscribeOn(Schedulers.newThread())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it.size == 5
                }
    }

    @Test
    fun getById() {
        val financeOperation = FinanceOperation(id = 0L,
                amount = BigDecimal("1000"),
                currency = Currency.Rubble,
                title = "title1",
                date = "today",
                category = OperationCategory.Education,
                state = FinanceOperationState.Done,
                accountKey = 0L,
                timeStart = System.currentTimeMillis(),
                timeFinish = System.currentTimeMillis() + 2000,
                type = OperationType.Expense)

        financeOperationDao.insert(financeOperation)

        financeOperationDao.getById(0L)
                .subscribeOn(Schedulers.newThread())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it == financeOperation
                }
    }

    @Test
    fun getByIdAndState() {
        val financeOperation = FinanceOperation(id = 0L,
                amount = BigDecimal("1000"),
                currency = Currency.Rubble,
                title = "title1",
                date = "today",
                category = OperationCategory.Education,
                state = FinanceOperationState.Done,
                accountKey = 0L,
                timeStart = System.currentTimeMillis(),
                timeFinish = System.currentTimeMillis() + 2000,
                type = OperationType.Expense)

        financeOperationDao.insert(financeOperation)

        financeOperationDao.getFinanceOperationsByIdAndInState(0L, FinanceOperationState.Done.name)
                .subscribeOn(Schedulers.newThread())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it.first() == financeOperation
                }
    }

    @Test
    fun getByAccountId() {
        val financeOperation = FinanceOperation(id = 0L,
                amount = BigDecimal("1000"),
                currency = Currency.Rubble,
                title = "title1",
                date = "today",
                category = OperationCategory.Education,
                state = FinanceOperationState.Done,
                accountKey = 0L,
                timeStart = System.currentTimeMillis(),
                timeFinish = System.currentTimeMillis() + 2000,
                type = OperationType.Expense)

        financeOperationDao.insert(financeOperation)

        financeOperationDao.getByAccountId(0L)
                .subscribeOn(Schedulers.newThread())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it.first() == financeOperation
                }
    }

    @After
    fun closeDb() {
        appDatabase.close()
    }
}