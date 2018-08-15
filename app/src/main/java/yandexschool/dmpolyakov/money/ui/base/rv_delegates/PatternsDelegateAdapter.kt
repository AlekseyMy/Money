package yandexschool.dmpolyakov.money.ui.base.rv_delegates

import com.example.delegateadapter.delegate.KDelegateAdapter
import kotlinx.android.synthetic.main.item_pattern.*
import yandexschool.dmpolyakov.money.OperationType
import yandexschool.dmpolyakov.money.R
import yandexschool.dmpolyakov.money.models.FinanceOperationPattern
import yandexschool.dmpolyakov.money.utils.getCompatColor

class PatternsDelegateAdapter(private val onOperation: (FinanceOperationPattern) -> Unit,
                              private val onRedo: (FinanceOperationPattern) -> Unit) :
        KDelegateAdapter<FinanceOperationPattern>() {

    override fun getLayoutId() = R.layout.item_pattern

    override fun isForViewType(items: MutableList<*>, position: Int) = items[position] is FinanceOperationPattern

    override fun onBind(item: FinanceOperationPattern, viewHolder: KViewHolder) = with(viewHolder) {

        title.text = item.title
        icon.setImageResource(item.category.icon)
        categoryTypeText.text = itemView.context.getString(item.category.title)

        val fuckingLint = "${item.amount} ${item.currency.sign}"  //можно использовать аннотацию suppressLint
        amount.text = fuckingLint

        amount.setTextColor(viewHolder.itemView.context.getCompatColor(
                when (item.type) {
                    OperationType.Income -> R.color.saturated_green
                    OperationType.Expense -> R.color.dark_scarlet
                }
        ))

        deleteImg.setOnClickListener { onOperation(item) }

        redoFinanceOperationImg.setOnClickListener { onRedo(item) }
    }

}