package yandexschool.dmpolyakov.money.ui.statistics

import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import yandexschool.dmpolyakov.money.OperationType
import yandexschool.dmpolyakov.money.models.FinanceOperation

class PieChartManager(private val chart: PieChart) {
    var chartData = listOf<FinanceOperation>()

    fun setData(operations: List<FinanceOperation>, type: OperationType) {
        chartData = operations
        setup(type)
    }

    private fun setup(type: OperationType) {
        val pieData = hashMapOf<String, Float>()

        for (item in chartData) {
            if (item.type == type) {
                val title = chart.context.getString(item.category.title)
                if (pieData[title] == null) {
                    pieData[title] = Math.abs(item.amount.toFloat())
                } else {
                    pieData[title] = (pieData[title] ?: 0F) + Math.abs(item.amount.toFloat())
                }
            }
        }

        val pieEntries = mutableListOf<PieEntry>()

        for (item in pieData) {
            val value = PieEntry(item.value, item.key)
            pieEntries.add(value)
        }

        val dataSet = PieDataSet(pieEntries, "")
        dataSet.apply {
            valueTextSize = 18f
            valueFormatter = PercentFormatter()
            colors = ColorTemplate.COLORFUL_COLORS.toList()
        }

        val data = PieData(dataSet)

        chart!!.apply {
            this.data = data
            setDrawEntryLabels(false)
            description?.isEnabled = false
            setUsePercentValues(true)

            legend?.apply {
                isWordWrapEnabled = false
                textSize = 12f
                isEnabled = false
            }
            invalidate()
            notifyDataSetChanged()
        }
    }
}