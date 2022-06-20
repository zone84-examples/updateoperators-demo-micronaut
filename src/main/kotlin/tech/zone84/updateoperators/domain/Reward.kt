package tech.zone84.updateoperators.domain

import tech.zone84.updateoperators.domain.grant.PurchaseDaysRewardable
import tech.zone84.updateoperators.domain.grant.RewardVisitor
import tech.zone84.updateoperators.domain.shared.Money
import tech.zone84.updateoperators.domain.shared.UserId

data class Reward(
    val userId: UserId,
    override val spentAmount: Money,
    override val purchaseDays: Set<String>
) : PurchaseDaysRewardable {
    override fun isGranted(visitor: RewardVisitor) = visitor.visitPurchaseDaysRewardable(this)

    companion object {
        fun emptyReward(userId: UserId) = Reward(
            userId = userId,
            spentAmount = Money.zero(),
            purchaseDays = setOf()
        )
    }
}

data class RewardUpdate(
    val userId: UserId,
    val amountIncrement: Money = Money.zero(),
    val newPurchaseDays: Set<String> = setOf()
)
