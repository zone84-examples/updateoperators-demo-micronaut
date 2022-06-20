package tech.zone84.updateoperators.domain

import tech.zone84.updateoperators.domain.shared.Money
import tech.zone84.updateoperators.domain.shared.UserId

data class PurchaseCommand(
    val userId: UserId,
    val totalAmount: Money
)
