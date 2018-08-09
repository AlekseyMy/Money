package yandexschool.dmpolyakov.money.ui.statistics

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.fragment_statistics.*
import yandexschool.dmpolyakov.money.OperationType
import yandexschool.dmpolyakov.money.R
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpFragment
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class StatisticsFragment : BaseMvpFragment<StatisticsPresenter>(), StatisticsView {

    companion object {
        val instance = StatisticsFragment()
    }

    @Inject
    lateinit var router: MainRouter

    @Inject
    @InjectPresenter
    lateinit var presenter: StatisticsPresenter

    @ProvidePresenter
    override fun providePresenter(): StatisticsPresenter = presenter

    override fun getLogName(): String = "Statistics fragment"

    private lateinit var pieChart: PieChartManager

    private val cal = Calendar.getInstance()

    private fun getDate(time: Date): String =
            SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(time)

    private val dateSetListenerSince = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, monthOfYear)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        sinceEditDate.setText(getDate(cal.time))
        presenter.setSince(cal.timeInMillis)
    }

    private val dateSetListenerUntil = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, monthOfYear)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        untilEditDate.setText(getDate(cal.time))
        presenter.setUntil(cal.timeInMillis)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_statistics, container, false)

    private fun setListeners() {
        val listener = { v: View, listener: DatePickerDialog.OnDateSetListener ->
            DatePickerDialog(context,
                    listener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        sinceEditDate.setOnClickListener { listener.invoke(it, dateSetListenerSince) }

        untilEditDate.setOnClickListener { listener.invoke(it, dateSetListenerUntil) }

        getStatImg.setOnClickListener {
            setTransactionDetails("", null, "")
            presenter.getTransactionInPeriod()
        }

        pieChartView.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                if (e != null) {
                    presenter.computeCategoryStatistics((e as PieEntry).label)
                }
            }

            override fun onNothingSelected() {
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pieChart = PieChartManager(pieChartView)
        setListeners()
        spIncomeExp.adapter = TypeArrayAdapter(context!!, OperationType.values().toList())
    }

    override fun setChartData(operations: List<FinanceOperation>) {
        pieChart.setData(operations, spIncomeExp.selectedItem as OperationType)
    }

    override fun setTransactionDetails(title: String, img: Int?, amount: String) {
        if (img != null) {
            categoryImg.setImageResource(img)
        } else {
            categoryImg.setImageDrawable(null)
        }
        catNameText.text = title
        catValueText.text = amount
    }

    override fun setTotal(value: String) {
        totalValueText.text = value
    }
}