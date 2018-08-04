package yandexschool.dmpolyakov.money.models

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE
import android.os.Parcelable
import com.example.delegateadapter.delegate.diff.IComparableItem
import kotlinx.android.parcel.Parcelize
import yandexschool.dmpolyakov.money.*
import yandexschool.dmpolyakov.money.models.converters.*
import java.math.BigDecimal

@Parcelize
@Entity(foreignKeys = [
     ForeignKey(
             entity = Account::class,
             parentColumns = ["id"],
             childColumns = ["account_id"],
             onDelete = CASCADE
     )]
)
@TypeConverters(AmountConverter::class,
        CurrencyConverter::class,
        OperationTypeConverter::class,
        OperationCategoryConverter::class,
        FinanceOperationStateConverter::class)
data class FinanceOperation(
        val title: String,
        val amount: BigDecimal,
        val type: OperationType,
        val category: OperationCategory,
        val currency: Currency,
        val date: String,
        val timeStart: Long,
        val timeFinish: Long,
        var state: FinanceOperationState,
        @ColumnInfo(name = "account_id") var accountKey: Long,
        @PrimaryKey(autoGenerate = true) private val id: Long? = null

) : Parcelable, IComparableItem {

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

    override fun content() = "$title$amount$type$currency$date"
}