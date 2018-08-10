package yandexschool.dmpolyakov.money.converters

import org.junit.Assert.assertEquals
import org.junit.Test
import yandexschool.dmpolyakov.money.Currency
import yandexschool.dmpolyakov.money.FinanceOperationState
import yandexschool.dmpolyakov.money.OperationCategory
import yandexschool.dmpolyakov.money.OperationType
import yandexschool.dmpolyakov.money.models.converters.*
import java.math.BigDecimal

class ConvertersTest {

    @Test
    fun testFinOpStateConverter_success() {
        for (item in FinanceOperationState.values()) {
            assertEquals(FinanceOperationStateConverter()
                    .fromFinanceOperationState(item), item.name)
            assertEquals(FinanceOperationStateConverter()
                    .toFinanceOperationState(item.name), item)
        }
    }

    @Test
    fun testAmountConverter_success() {
        assertEquals(AmountConverter().fromBigDecimal(BigDecimal("5.0")), "5.0")
        assertEquals(AmountConverter().toBigDecimal("10"), BigDecimal("10"))
    }

    @Test
    fun testCurrencyConverter_success() {
        for (item in Currency.values()) {
            assertEquals(CurrencyConverter().toCurrency(item.name), item)
            assertEquals(CurrencyConverter().fromCurrency(item), item.name)
        }
    }

    @Test
    fun testOperationCategoryConverter_success() {
        for (item in OperationCategory.values()) {
            assertEquals(OperationCategoryConverter().fromOperationCategory(item), item.name)
            assertEquals(OperationCategoryConverter().toOperationCategory(item.name), item)
        }
    }

    @Test
    fun testOperationTypeConverter_success() {
        for (item in OperationType.values()) {
            assertEquals(OperationTypeConverter().fromOperationType(item), item.name)
            assertEquals(OperationTypeConverter().toOperationType(item.name), item)
        }
    }
}