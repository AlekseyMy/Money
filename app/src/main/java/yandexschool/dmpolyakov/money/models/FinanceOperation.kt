package yandexschool.dmpolyakov.money.models

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE
import android.os.Parcelable
import com.example.delegateadapter.delegate.diff.IComparableItem
import kotlinx.android.parcel.Parcelize
import yandexschool.dmpolyakov.money.Currency
import yandexschool.dmpolyakov.money.DOLLAR_TO_RUBBLE
import yandexschool.dmpolyakov.money.OperationCategory
import yandexschool.dmpolyakov.money.OperationType
import yandexschool.dmpolyakov.money.models.converters.AmountConverter
import yandexschool.dmpolyakov.money.models.converters.CurrencyConverter
import yandexschool.dmpolyakov.money.models.converters.OperationCategoryConverter
import yandexschool.dmpolyakov.money.models.converters.OperationTypeConverter
import java.math.BigDecimal

@Parcelize
@Entity(foreignKeys = [
     ForeignKey(
             entity = Account::class,
             parentColumns = ["id"],
             childColumns = ["account_key"],
             onDelete = CASCADE
     )]
)
@TypeConverters(AmountConverter::class,
        CurrencyConverter::class,
        OperationTypeConverter::class,
        OperationCategoryConverter::class)
data class FinanceOperation(
        var title: String,
        var amount: BigDecimal,
        var type: OperationType,
        var category: OperationCategory,
        var currency: Currency,
        var date: String,
        @ColumnInfo(name = "account_key")
        var accountKey: Long,
        @PrimaryKey(autoGenerate = true)
        private var id: Long

) : Parcelable, IComparableItem {

    constructor(): this("", BigDecimal.ONE,
            OperationType.Income,
            OperationCategory.Education,
            Currency.Dollar,
            "",
            0,
            0)

    fun getDifferenceInRubbles(): BigDecimal {
        val rubbles = when (currency) {
            Currency.Dollar -> amount * DOLLAR_TO_RUBBLE.toBigDecimal()
            Currency.Rubble -> amount
        }

        return when (type) {
            OperationType.Income -> rubbles
            OperationType.Expense -> rubbles * BigDecimal(-1)
        }
    }

    override fun id() = id
    fun setId(value: Long) {
        id = value
    }
    override fun content() = "$title$amount$type$currency$date"
}