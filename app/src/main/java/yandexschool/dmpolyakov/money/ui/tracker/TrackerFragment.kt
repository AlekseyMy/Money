package yandexschool.dmpolyakov.money.ui.tracker

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.delegateadapter.delegate.diff.DiffUtilCompositeAdapter
import com.example.delegateadapter.delegate.diff.IComparableItem
import kotlinx.android.synthetic.main.fragment_tracker.*
import yandexschool.dmpolyakov.money.*
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.ui.MainActivity
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpFragment
import yandexschool.dmpolyakov.money.ui.base.rv_delegates.AccountDelegateAdapter
import yandexschool.dmpolyakov.money.ui.base.rv_delegates.EmptyStateDelegateAdapter
import yandexschool.dmpolyakov.money.ui.base.rv_delegates.SubtitleDelegateAdapter
import yandexschool.dmpolyakov.money.ui.base.rv_delegates.view_models.EmptyStateViewModel
import yandexschool.dmpolyakov.money.ui.base.rv_delegates.view_models.SubtitleViewModel
import yandexschool.dmpolyakov.money.ui.tracker.account.operations.CategoryArrayAdapter
import yandexschool.dmpolyakov.money.utils.*
import java.math.BigDecimal
import javax.inject.Inject


class TrackerFragment : BaseMvpFragment<TrackerPresenter>(), TrackerView {

    companion object {
        val instance = TrackerFragment()
    }

    @Inject
    lateinit var router: MainRouter

    @Inject
    @InjectPresenter
    lateinit var presenter: TrackerPresenter

    private lateinit var addOldOperationDialog: AlertDialog

    @ProvidePresenter
    override fun providePresenter(): TrackerPresenter {
        return presenter
    }

    private lateinit var addNewAccountDialog: AlertDialog

    private val accountAdapter = DiffUtilCompositeAdapter.Builder()
            .add(AccountDelegateAdapter {
                presenter.onAccountClick(it)
            })
            .add(EmptyStateDelegateAdapter())
            .add(SubtitleDelegateAdapter())
            .build()

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBottomNavigationMenu()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? = inflater.inflate(R.layout.fragment_tracker, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = accountAdapter

        addAccount.setOnClickListener {
            showDialog()
        }

        addNewAccountDialog = AlertDialog.Builder(view.context)
                .setView(R.layout.dialog_add_new_account)
                .setCancelable(false)
                .create()

        addOldOperationDialog = AlertDialog.Builder(view.context)
                .setView(R.layout.dialog_add_new_operation)
                .setCancelable(false)
                .create()
    }

    override fun showFinanceOperationDialog(financeOperation: FinanceOperation) {
        with(addOldOperationDialog) {
            show()

            val title = findViewById<TextInputLayout>(R.id.title)
            val amount = findViewById<TextInputLayout>(R.id.amount)
            val type = findViewById<RadioGroup>(R.id.type)

            val currency = findViewById<Spinner>(R.id.spinnerCurrency)
            val category = findViewById<Spinner>(R.id.spinnerCategory)

            val days = findViewById<EditText>(R.id.inputDays)

            if (BuildConfig.DEBUG) {
                days?.hint = resources.getString(R.string.debug_repeat_across)
            } else {
                days?.hint = resources.getString(R.string.repeat_across)
            }

            title?.editText?.setText(financeOperation.title)
            amount?.editText?.setText(financeOperation.amount.toString())

            days?.hint = if (BuildConfig.DEBUG)
                (financeOperation.timeFinish - financeOperation.timeStart).millsToSeconds().toString()
            else
                (financeOperation.timeFinish - financeOperation.timeStart).millsToDays().toString()

            currency?.adapter = CurrencyArrayAdapter(context, Currency.values().toList())
            category?.adapter = CategoryArrayAdapter(context, OperationType.Income.getCategories())

            currency?.setSelection(Currency.values().toList().indexOf(financeOperation.currency))
            category?.setSelection(OperationType.Income.getCategories().indexOf(financeOperation.category))

            type?.setOnCheckedChangeListener { radioGroup, id ->
                val adapter = (category?.adapter as CategoryArrayAdapter)
                adapter.clear()
                when (id) {
                    R.id.income -> adapter.addAll(OperationType.Income.getCategories())
                    R.id.expense -> adapter.addAll(OperationType.Expense.getCategories())
                }
                adapter.notifyDataSetChanged()
            }

            findViewById<View>(R.id.cancel)?.setOnClickListener {
                hideFinanceOperationDialog()
            }

            findViewById<View>(R.id.add)?.setOnClickListener {
                if (title?.editText?.text.toString().isBlank()) {
                    title?.error = getString(yandexschool.dmpolyakov.money.R.string.error_empty_title)
                    return@setOnClickListener
                }

                if (amount?.editText?.text.toString().isBlank()) {
                    amount?.error = getString(yandexschool.dmpolyakov.money.R.string.error_empty_amount)
                    return@setOnClickListener
                }

                val df = java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.getDefault())
                val currentDate = df.format(java.util.Calendar.getInstance().time)

                val operationType: OperationType = when (type?.checkedRadioButtonId) {
                    R.id.income -> OperationType.Income
                    R.id.expense -> OperationType.Expense
                    else -> throw Exception("Unknown operation type")
                }

                val time = timeNow()
                val timeFinish = time + if (BuildConfig.DEBUG)
                    days?.text.toString().secondsToMills()
                else
                    days?.text.toString().daysToMillis()

                accountAdapter
                financeOperation.state = FinanceOperationState.Done
                presenter.updateOperation(financeOperation)
                presenter.addOperation(
                        FinanceOperation(
                                title = title?.editText?.text.toString(),
                                currency = currency?.selectedItem as Currency,
                                amount = BigDecimal(amount?.editText?.text?.toString()),
                                type = operationType,
                                category = category?.selectedItem as OperationCategory,
                                date = currentDate,
                                timeStart = time,
                                timeFinish = timeFinish,
                                accountKey = 0,
                                state = if (timeFinish > time)
                                    FinanceOperationState.InProgress
                                else
                                    FinanceOperationState.Done
                        )
                )

                dismiss()
                presenter.showNext()
            }

            setOnDismissListener {
                title?.editText?.setText("")
                title?.isErrorEnabled = false
                amount?.editText?.setText("")
                amount?.isErrorEnabled = false
                currency?.setSelection(0)
                title?.requestFocus()
                financeOperation.state = FinanceOperationState.Canceled
                presenter.updateOperation(financeOperation)
                presenter.showNext()
            }
        }
    }

    override fun hideFinanceOperationDialog() {
        addOldOperationDialog.apply {
            if (isShowing) {
                dismiss()
            }
        }
    }

    override fun showDialog() {
        with(addNewAccountDialog) {
            show()

            val title = findViewById<TextInputLayout>(R.id.title)
            val amountInput = findViewById<EditText>(R.id.inputAmount)

            val currency = findViewById<Spinner>(R.id.spinnerCurrency)
            currency?.adapter = CurrencyArrayAdapter(context, Currency.values().toList())

            findViewById<View>(R.id.cancel)?.setOnClickListener {
                presenter.onCancelAddAccountDialog()
            }

            findViewById<View>(R.id.create)?.setOnClickListener {
                var amount = amountInput?.text.toString()
                if (amount.isBlank()) amount = "0"

                if (title?.editText?.text.toString().isBlank()) {
                    title?.error = getString(R.string.error_empty_title)
                    return@setOnClickListener
                }

                presenter.addAccount(
                        Account(
                                title = title?.editText?.text.toString(),
                                amount = BigDecimal(amount),
                                currency = currency?.selectedItem as Currency,
                                id = 0L))
                hideAddAccountDialog()
            }

            setOnDismissListener {
                Log.wtf("dima", "DISMISS")
                title?.editText?.setText("")
                title?.isErrorEnabled = false
                amountInput?.setText("")
                currency?.setSelection(0)
                title?.requestFocus()
            }
        }
    }

    override fun hideAddAccountDialog() {
        addNewAccountDialog.apply {
            if (isShowing) {
                dismiss()
            }
        }
    }

    override fun showBalance(count: BigDecimal, currency: Currency) {

    }

    override fun showAccounts(accounts: List<Account>) {
        val data = ArrayList<IComparableItem>()
        if (accounts.isNotEmpty()) {
            data.add(SubtitleViewModel("${getString(R.string.my_accounts)}:"))
            data.addAll(accounts)
        }
        data.add(EmptyStateViewModel(getString(R.string.no_accounts)))
        accountAdapter.swapData(data)
    }

    override fun getLogName() = "TrackerFragment"

    override fun onStop() {
        if (addNewAccountDialog.isShowing) {
            addNewAccountDialog.dismiss()
        }
        super.onStop()
    }
}