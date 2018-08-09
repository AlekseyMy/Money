package yandexschool.dmpolyakov.money.ui.tracker.account.financepatterns

import android.app.AlertDialog
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.delegateadapter.delegate.diff.DiffUtilCompositeAdapter
import com.example.delegateadapter.delegate.diff.IComparableItem
import kotlinx.android.synthetic.main.dialog_add_new_operation.*
import kotlinx.android.synthetic.main.fragment_finance_patterns.*
import yandexschool.dmpolyakov.money.*
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.models.FinanceOperationPattern
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpFragment
import yandexschool.dmpolyakov.money.ui.base.rv_delegates.EmptyStateDelegateAdapter
import yandexschool.dmpolyakov.money.ui.base.rv_delegates.PatternsDelegateAdapter
import yandexschool.dmpolyakov.money.ui.base.rv_delegates.view_models.EmptyStateViewModel
import yandexschool.dmpolyakov.money.ui.tracker.CurrencyArrayAdapter
import yandexschool.dmpolyakov.money.ui.tracker.account.operations.CategoryArrayAdapter
import yandexschool.dmpolyakov.money.utils.daysToMillis
import yandexschool.dmpolyakov.money.utils.secondsToMills
import java.math.BigDecimal
import javax.inject.Inject

class FinancePatternsFragment : BaseMvpFragment<FinancePatternsPresenter>(), FinancePatternsView {

    @Inject
    lateinit var router: MainRouter

    @Inject
    @InjectPresenter
    lateinit var presenter: FinancePatternsPresenter

    @ProvidePresenter
    override fun providePresenter(): FinancePatternsPresenter = presenter

    private lateinit var addPatternOperationDialog: AlertDialog

    private val financePatternsAdapter = DiffUtilCompositeAdapter.Builder()
            .add(PatternsDelegateAdapter ({ presenter.deletePattern(it) },
                { showFinanceOperationDialog(it) }))
            .add(EmptyStateDelegateAdapter())
            .build()

    override fun loadAccount() {
        val account = arguments?.getParcelable<Account>("account")!!
        presenter.setAccount(account)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_finance_patterns, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPatterns.layoutManager = LinearLayoutManager(context)
        rvPatterns.adapter = financePatternsAdapter

        addPatternOperationDialog = AlertDialog.Builder(view.context)
                .setView(R.layout.dialog_add_new_operation)
                .setCancelable(false)
                .create()
    }

    override fun showOperations(patterns: List<FinanceOperationPattern>) {
        val data = ArrayList<IComparableItem>()
        data.addAll(patterns.reversed())
        data.add(EmptyStateViewModel(getString(R.string.empty_patterns_list)))
        financePatternsAdapter.swapData(data)
        rvPatterns.scrollToPosition(0)
    }

    //понимаю, что дублирую код
    fun showFinanceOperationDialog(financeOperationPattern: FinanceOperationPattern) {
        with(addPatternOperationDialog) {
            show()

            val title: TextInputLayout? = titlePeriodic
            val amount: TextInputLayout? = amountPeriodic
            val type: RadioGroup? = type

            val currency: Spinner? = spinnerCurrency
            val category: Spinner? = spinnerCategory
            val doPattern: CheckBox? = doPatternCheck

            doPattern?.visibility = View.GONE

            val days: EditText? = inputDays

            if (BuildConfig.DEBUG) {
                days?.hint = resources.getString(R.string.debug_repeat_across)
            } else {
                days?.hint = resources.getString(R.string.repeat_across)
            }

            title?.editText?.setText(financeOperationPattern.title)
            amount?.editText?.setText(financeOperationPattern.amount.abs().toString())

            currency?.adapter = CurrencyArrayAdapter(context, Currency.values().toList())
            category?.adapter = CategoryArrayAdapter(context, OperationType.Income.getCategories())

            currency?.setSelection(Currency.values().toList().indexOf(financeOperationPattern.currency))
            category?.setSelection(OperationType.Income.getCategories().indexOf(financeOperationPattern.category))

            when (financeOperationPattern.type) {
                OperationType.Income -> type?.check(R.id.income)
                OperationType.Expense -> type?.check(R.id.expense)
            }
            type?.checkedRadioButtonId
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

                val time = System.currentTimeMillis()
                val timeFinish = time + if (BuildConfig.DEBUG)
                    days?.text.toString().secondsToMills()
                else
                    days?.text.toString().daysToMillis()

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
            }

            setOnDismissListener {
                title?.editText?.setText("")
                title?.isErrorEnabled = false
                amount?.editText?.setText("")
                amount?.isErrorEnabled = false
                currency?.setSelection(0)
                title?.requestFocus()
            }
        }
    }

    override fun hideFinanceOperationDialog() {
        addPatternOperationDialog.apply {
            if (isShowing) {
                dismiss()
            }
        }
    }

    override fun getLogName(): String = "FinancePatternsFragment"
}