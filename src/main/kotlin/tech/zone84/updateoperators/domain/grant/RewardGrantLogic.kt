package tech.zone84.updateoperators.domain.grant

class RewardGrantLogic(private val settings: RewardSettings) : RewardVisitor {
    override fun visitPurchaseDaysRewardable(element: PurchaseDaysRewardable): Boolean {
        return element.spentAmount >= settings.minimumAmount && element.purchaseDays.size >= settings.minimumPurchaseDays
    }
}
