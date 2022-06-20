package tech.zone84.updateoperators.domain.grant

import tech.zone84.updateoperators.domain.shared.Money

interface Rewardable {
    fun isGranted(visitor: RewardVisitor): Boolean
}

interface PurchaseDaysRewardable: Rewardable {
    val spentAmount: Money
    val purchaseDays: Set<String>
}
