package yandexschool.dmpolyakov.money.androidjunit

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.runner.RunWith
import yandexschool.dmpolyakov.money.storage.AppDatabase
import android.arch.persistence.room.Room
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Test
import yandexschool.dmpolyakov.money.Currency
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.storage.AccountDao
import java.math.BigDecimal
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class AccountDaoInstrumentedTest {

    private lateinit var accountDao: AccountDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getTargetContext()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java!!).build()
        accountDao = appDatabase.accountDao
    }

    fun fillHelper(range: LongRange) {
        for (index in range) {
            accountDao.insert(Account(id = index,
                    amount = BigDecimal("1000"),
                    currency = Currency.Rubble,
                    title = "title1"))
        }
    }

    @Test
    fun getAll() {
        fillHelper(0L..1L)
        accountDao.getAll()
                .subscribeOn(Schedulers.newThread())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it.size == 2
                }
    }

    @Test
    fun getById() {
        fillHelper(0L..1L)
        val targetId = 1L

        accountDao.getById(targetId)
                .subscribeOn(Schedulers.newThread())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it.id() == targetId
                }
    }

    @Test
    fun getById_returnsEmpty() {
        fillHelper(0L..0L)
        val targetId = 1L

        accountDao.getById(targetId)
                .subscribeOn(Schedulers.newThread())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertEmpty()
    }

    @Test
    fun getAll_returnEmpty() {
        accountDao.getAll()
                .subscribeOn(Schedulers.newThread())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it.isEmpty()
                }
    }

    @Test
    fun updateTitle() {
        fillHelper(0L..1L)
        val newTitle = "hardTitle"

        accountDao.updateTitle(newTitle, 1L)

        accountDao.getById(1L)
                .subscribeOn(Schedulers.io())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it.title == newTitle
                }
    }

    @After
    fun closeDb() {
        appDatabase.close()
    }
}