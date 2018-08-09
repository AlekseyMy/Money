package yandexschool.dmpolyakov.money.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.os.Parcelable
import com.example.delegateadapter.delegate.diff.IComparableItem
import kotlinx.android.parcel.Parcelize
import yandexschool.dmpolyakov.money.Currency
import yandexschool.dmpolyakov.money.models.converters.AmountConverter
import yandexschool.dmpolyakov.money.models.converters.CurrencyConverter
import java.math.BigDecimal
import java.text.DecimalFormat

@Parcelize
@Entity
@TypeConverters(AmountConverter::class, CurrencyConverter::class)
data class Account(
        var title: String,
        var amount: BigDecimal,
        val currency: Currency,
        @PrimaryKey(autoGenerate = true) private val id: Long? = null
) : Parcelable, IComparableItem {

    val balance
        get() = DecimalFormat("0.00").format(amount) + " ${currency.sign}"

    override fun id() = id

    override fun content() = "$title $amount $currency"
}