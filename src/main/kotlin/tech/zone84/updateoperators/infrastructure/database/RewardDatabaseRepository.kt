package tech.zone84.updateoperators.infrastructure.database

import jakarta.inject.Singleton
import tech.zone84.updateoperators.domain.Reward
import tech.zone84.updateoperators.domain.RewardUpdate
import tech.zone84.updateoperators.domain.repository.RewardRepository
import tech.zone84.updateoperators.domain.shared.Money
import tech.zone84.updateoperators.domain.shared.UserId

@Singleton
class RewardDatabaseRepository(private val mongoRepository: RewardMongoRepository) : RewardRepository {
    override fun updateReward(update: RewardUpdate) {
        mongoRepository.update(
            userId = update.userId.raw,
            spentAmountIncrement = update.amountIncrement.amount,
            newPurchaseDays = update.newPurchaseDays
        )
    }

    override fun findReward(userId: UserId): Reward? = mongoRepository.findByUserId(userId.raw)?.let {
        Reward(
            userId = UserId.from(it.userId),
            spentAmount = Money(it.spentAmount),
            purchaseDays = it.purchaseDays.toSet()
        )
    }
}
