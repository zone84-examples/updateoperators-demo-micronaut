package tech.zone84.updateoperators.domain

import tech.zone84.updateoperators.domain.grant.RewardGrantLogic
import tech.zone84.updateoperators.domain.grant.RewardSettings
import tech.zone84.updateoperators.domain.repository.RewardRepository
import tech.zone84.updateoperators.domain.repository.TimeProvider
import tech.zone84.updateoperators.domain.shared.UserId

class Domain(val rewardCalculator: RewardCalculator) {
    fun findReward(userId: UserId) = rewardCalculator.findReward(userId)
    fun rewardPurchase(command: PurchaseCommand) = rewardCalculator.rewardPurchase(command)

    companion object {
        fun build(rewardRepository: RewardRepository, rewardSettings: RewardSettings, timeProvider: TimeProvider): Domain {
            val rewardVisitor = RewardGrantLogic(rewardSettings)
            val rewardCalculator = RewardCalculator(rewardRepository, rewardVisitor, timeProvider)
            return Domain(rewardCalculator)
        }
    }
}
