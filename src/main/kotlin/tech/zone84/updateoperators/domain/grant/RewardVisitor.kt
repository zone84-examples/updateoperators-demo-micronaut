package tech.zone84.updateoperators.domain.grant

/**
 * This is a rather simple visitor that allows decoupling checking reward conditions from both the reward calculator,
 * and the [tech.zone84.updateoperators.domain.Reward] entity. At the same time, it is still possible to control what
 * checks to apply by implementing the proper interface on the entity. This solution can be extended for more types
 * of checks by creating additional interfaces that extend [Rewardable] and by adding new `visit...()` functions here.
 */
interface RewardVisitor {
    fun visitPurchaseDaysRewardable(element: PurchaseDaysRewardable): Boolean
}
