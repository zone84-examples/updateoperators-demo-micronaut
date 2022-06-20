package tech.zone84.updateoperators.domain

import tech.zone84.updateoperators.domain.shared.Money
import tech.zone84.updateoperators.domain.shared.UserId

data class RewardDetails(
    val userId: UserId,
    val spentAmount: Money,
    val purchaseDayCount: Int,
    val granted: Boolean
)
