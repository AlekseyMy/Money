package yandexschool.dmpolyakov.money.ui.base.rv_delegates

import com.example.delegateadapter.delegate.KDelegateAdapter
import kotlinx.android.synthetic.main.item_periodic_operation.*
import yandexschool.dmpolyakov.money.OperationType
import yandexschool.dmpolyakov.money.R
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.utils.getCompatColor

class PeriodicOperationsDelegateAdapter(private val onOperation: (FinanceOperation) -> Unit) : KDelegateAdapter<FinanceOperation>() {

    override fun getLayoutId() = R.layout.item_periodic_operation

    override fun isForViewType(items: MutableList<*>, position: Int) = items[position] is FinanceOperation

    override fun onBind(item: FinanceOperation, viewHolder: KViewHolder) = with(viewHolder) {

        datePeriodic.text = item.date
        titlePeriodic.text = item.title
        iconPeriodic.setImageResource(item.category.icon)

        val s = "${item.amount} ${item.currency.sign}"
        amountPeriodic.text = s

        deleteImageView.setOnClickListener {
            onOperation(item)
        }

        amountPeriodic.setTextColor(viewHolder.itemView.context.getCompatColor(
                when (item.type) {
                    OperationType.Income -> R.color.saturated_green
                    OperationType.Expense -> R.color.dark_scarlet
                }
        ))

    }
}