package tech.zone84.updateoperators.infrastructure.settings

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Property
import tech.zone84.updateoperators.domain.grant.RewardSettings
import tech.zone84.updateoperators.domain.shared.Money
import java.math.BigDecimal
import javax.validation.constraints.Min

@ConfigurationProperties("rewards")
data class ConfigurationRewardSettings(
    @Property(name = "rewards.minimumAmount")
    val minimumAmountRaw: BigDecimal,
    @Min(1L)
    @Property(name = "rewards.minimumPurchaseDays")
    override val minimumPurchaseDays: Int
) : RewardSettings {
    override val minimumAmount: Money
        get() = Money(minimumAmountRaw)
}
