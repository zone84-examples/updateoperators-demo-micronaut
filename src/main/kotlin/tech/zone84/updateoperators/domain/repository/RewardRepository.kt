package tech.zone84.updateoperators.domain.repository

import tech.zone84.updateoperators.domain.Reward
import tech.zone84.updateoperators.domain.RewardUpdate
import tech.zone84.updateoperators.domain.shared.UserId

interface RewardRepository {
    fun updateReward(update: RewardUpdate)
    fun findReward(userId: UserId): Reward?
}