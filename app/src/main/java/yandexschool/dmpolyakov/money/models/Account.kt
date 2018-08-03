package yandexschool.dmpolyakov.money.models

import android.arch.persistence.room.*
import android.os.Parcelable
import com.example.delegateadapter.delegate.diff.IComparableItem
import kotlinx.android.parcel.Parcelize
import yandexschool.dmpolyakov.money.Currency
import yandexschool.dmpolyakov.money.OperationType
import yandexschool.dmpolyakov.money.models.converters.*
import yandexschool.dmpolyakov.money.utils.toDollars
import yandexschool.dmpolyakov.money.utils.toRubbles
import java.math.BigDecimal
import java.text.DecimalFormat

@Parcelize
@Entity
@TypeConverters(AmountConverter::class, CurrencyConverter::class)
data class Account(
        var title: String,
        val amount: BigDecimal,
        val currency: Currency,
        @PrimaryKey(autoGenerate = true) private val id: Long
) : Parcelable, IComparableItem {

    val balance
        get() = DecimalFormat("0.00").format(amount) + " ${currency.sign}"

    override fun id() = id

    override fun content() = "$title $amount $currency"
}