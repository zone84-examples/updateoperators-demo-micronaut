package tech.zone84.updateoperators.infrastructure.rest

import tech.zone84.updateoperators.domain.PurchaseCommand
import tech.zone84.updateoperators.domain.RewardDetails
import tech.zone84.updateoperators.domain.shared.Money
import tech.zone84.updateoperators.domain.shared.UserId

object RewardPurchasedServiceMapper {
    fun toDomain(input: RewardPurchasedServiceRequest) = with(input) {
        PurchaseCommand(
            userId = UserId.from(userId),
            totalAmount = Money.from(totalAmount)
        )
    }
}

data class RewardPurchasedServiceRequest(
    /**
     * Note: in the production code, you *want* to retrieve JWT tokens and validate the passed user ID against them.
     */
    val userId: String,
    val totalAmount: String
)

object RewardMapper {
    fun fromDomain(input: RewardDetails) = with(input) {
        RewardResponse(
            userId = userId.raw,
            spentAmount = spentAmount.amount.toPlainString(),
            purchaseDayCount = purchaseDayCount,
            granted = granted
        )
    }
}

data class RewardResponse(
    val userId: String,
    val spentAmount: String,
    val purchaseDayCount: Int,
    val granted: Boolean
)