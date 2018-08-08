package yandexschool.dmpolyakov.money.ui.statistics

import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import yandexschool.dmpolyakov.money.models.FinanceOperation

class PieChartManager(private val chart: PieChart) {
    var chartData = listOf<FinanceOperation>()

    fun setData(operations: List<FinanceOperation>) {
        chartData = operations
        setup()
    }

    private fun setup() {
        val pieData = hashMapOf<String, Float>()

        for (item in chartData) {
            val title = chart.context.getString(item.category.title)
            if (pieData[title] == null) {
                pieData[title] = Math.abs(item.amount.toFloat())
            } else {
                pieData[title] = (pieData[title] ?: 0F) + Math.abs(item.amount.toFloat())
            }
        }

        val pieEntries = mutableListOf<PieEntry>()

        for (item in pieData) {
            val value = PieEntry(item.value, item.key)
            pieEntries.add(value)
        }

        val dataSet = PieDataSet(pieEntries, "")
        dataSet.valueTextSize = 18f
        val data = PieData(dataSet)
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()

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