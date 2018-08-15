package yandexschool.dmpolyakov.money.repositories

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import yandexschool.dmpolyakov.money.Base.BaseUnitTest
import yandexschool.dmpolyakov.money.Currency
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.repository.AccountRepository
import yandexschool.dmpolyakov.money.repository.AccountRepositoryImpl
import yandexschool.dmpolyakov.money.storage.AccountDao
import yandexschool.dmpolyakov.money.storage.AppDatabase
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

class AccountRepositoryImplTest: BaseUnitTest() {

    lateinit var accountRepository: AccountRepository

    @Mock
    lateinit var db: AppDatabase

    @Mock
    lateinit var accountDao: AccountDao

    private val accountsList = listOf(
            Account(id = 0L, currency = Currency.Rubble, amount = BigDecimal("0.0"), title = "title1"),
            Account(id = 1L, currency = Currency.Rubble, amount = BigDecimal("0.0"), title = "title2")
    )

    @Before
    override fun onInit() {
        super.onInit()

        accountRepository = AccountRepositoryImpl(db)
    }

    override fun onMockInit() {

    }

    @Test
    fun getAccounts_success() {
        doReturn(accountDao).`when`(db).accountDao
        doReturn(Flowable.just(accountsList)).`when`(accountDao).getAll()

        accountRepository.getAccounts()
                .subscribeOn(Schedulers.newThread())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it === accountsList
                }
    }

    @Test
    fun addAccount_success() {
        val bd = mutableListOf<Account>()
        val account = Account(id = 1L, currency = Currency.Rubble, amount = BigDecimal("0.0"), title = "title2")

        doReturn(accountDao).`when`(db).accountDao

        doAnswer { Completable.fromAction { bd.add(account.copy()) } }.`when`(accountDao).insert(account)

        accountRepository.addAccount(account)
                .subscribeOn(Schedulers.newThread())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertComplete()
    }

    @Test
    fun getById_success() {
        doReturn(accountDao).`when`(db).accountDao
        doReturn(Flowable.just(accountsList[1])).`when`(accountDao).getById(1L)

        accountRepository.getAccount(1L)
                .subscribeOn(Schedulers.newThread())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it === accountsList[1]
                }
    }
}