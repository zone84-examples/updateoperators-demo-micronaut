package tech.zone84.updateoperators.domain.shared

import java.math.BigDecimal

/**
 * Note: this is a huge simplification just for demo purposes. For production scenarios, consider
 * using money libraries, such as JavaMoney API (JSR-354) and Moneta library.
 */
@JvmInline
value class Money(val amount: BigDecimal) {
    operator fun plus(money: Money): Money = Money(this.amount + money.amount)
    operator fun compareTo(money: Money): Int = this.amount.compareTo(money.amount)

    fun asString() = amount.toPlainString()

    companion object {
        fun zero() = Money(BigDecimal.ZERO)
        fun from(value: String) = Money(BigDecimal(value))
    }
}
