package yandexschool.dmpolyakov.money.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.example.delegateadapter.delegate.diff.IComparableItem
import kotlinx.android.parcel.Parcelize
import yandexschool.dmpolyakov.money.Currency
import yandexschool.dmpolyakov.money.OperationType
import yandexschool.dmpolyakov.money.utils.toDollars
import yandexschool.dmpolyakov.money.utils.toRubbles
import java.math.BigDecimal
import java.text.DecimalFormat

@Parcelize
@Entity
data class Account(
        var title: String,
        @Ignore
        var amount: BigDecimal,
        @Ignore
        val currency: Currency,
        @Ignore
        val operations: ArrayList<FinanceOperation> = ArrayList(),
        @PrimaryKey(autoGenerate = true)
        private var id: Long
) : Parcelable, IComparableItem {

    constructor(): this("",
            BigDecimal.ONE,
            Currency.Dollar,
            arrayListOf<FinanceOperation>(),
            0)

    val balance
        get() = DecimalFormat("0.00").format(amount) + " ${currency.sign}"

    fun addFinanceOperation(operation: FinanceOperation) {
        val extra = when (currency) {
            Currency.Rubble -> operation.amount.toRubbles(operation.currency)
            Currency.Dollar -> operation.amount.toDollars(operation.currency)
        }
        amount = amount.add(if (operation.type == OperationType.Income) extra else extra.negate())
        operations.add(operation)
    }

    override fun id() = id
    fun setId(value: Long) {
        id = value
    }
    override fun content() = "$title $amount $currency"
}