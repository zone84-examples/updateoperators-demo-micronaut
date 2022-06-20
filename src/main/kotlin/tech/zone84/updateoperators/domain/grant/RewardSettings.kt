package tech.zone84.updateoperators.domain.grant

import tech.zone84.updateoperators.domain.shared.Money

interface RewardSettings {
    val minimumAmount: Money
    val minimumPurchaseDays: Int
}
