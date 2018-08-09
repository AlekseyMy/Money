package yandexschool.dmpolyakov.money.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import yandexschool.dmpolyakov.money.Currency
import yandexschool.dmpolyakov.money.OperationCategory
import yandexschool.dmpolyakov.money.OperationType
import yandexschool.dmpolyakov.money.models.converters.AmountConverter
import yandexschool.dmpolyakov.money.models.converters.CurrencyConverter
import yandexschool.dmpolyakov.money.models.converters.OperationCategoryConverter
import yandexschool.dmpolyakov.money.models.converters.OperationTypeConverter
import java.math.BigDecimal

@Parcelize
@Entity
@TypeConverters(AmountConverter::class,
        CurrencyConverter::class,
        OperationTypeConverter::class,
        OperationCategoryConverter::class)
data class FinanceOperationPattern(
        @PrimaryKey(autoGenerate = true) val id: Long? = null,
        val title: String,
        val amount: BigDecimal,
        val type: OperationType,
        val category: OperationCategory,
        val currency: Currency) : Parcelable