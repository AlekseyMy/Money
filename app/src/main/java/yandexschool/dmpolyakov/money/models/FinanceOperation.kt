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
data class FinanceOperation(
        @PrimaryKey(autoGenerate = true)
        private var id: Long,
        var title: String,
        @Ignore
        val amount: BigDecimal,
        @Ignore
        val type: OperationType,
        @Ignore
        val category: OperationCategory,
        @Ignore
        var currency: Currency,
        var date: String,
        @ColumnInfo(name = "account_key")
        var accountKey: Long

) : Parcelable, IComparableItem {

    constructor(): this(0, "", BigDecimal.ONE,
            OperationType.Income,
            OperationCategory.Education,
            Currency.Dollar,
            "",
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