package tech.zone84.updateoperators.domain

import mu.KotlinLogging
import tech.zone84.updateoperators.domain.grant.RewardVisitor
import tech.zone84.updateoperators.domain.repository.RewardRepository
import tech.zone84.updateoperators.domain.repository.TimeProvider
import tech.zone84.updateoperators.domain.shared.UserId
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Note: this implementation is vulnerable to the configuration changes. If you change e.g. the number of purchase
 * days, the previously granted rewards may be discarded upon calling [rewardPurchase]. In real life, attempts to
 * change the reward configuration might not be allowed due to legal reasons. However, you can try to protect
 * this code against this side effect by storing the settings in the reward document upon the first save. It
 * will require some rework of the code below.
 */
class RewardCalculator(
    private val repository: RewardRepository,
    private val rewardVisitor: RewardVisitor,
    private val timeProvider: TimeProvider
) {
    fun rewardPurchase(command: PurchaseCommand) {
        logger.info { "Updating reward information due to a service purchased by user: '${command.userId.raw}'" }
        val item = repository.findReward(command.userId) ?: Reward.emptyReward(command.userId)
        if (item.isGranted(rewardVisitor)) {
            repository.updateReward(
                RewardUpdate(
                    userId = command.userId,
                    amountIncrement = command.totalAmount
                )
            )
        } else {
            repository.updateReward(
                RewardUpdate(
                    userId = command.userId,
                    amountIncrement = command.totalAmount,
                    newPurchaseDays = setOf(currentDate())
                )
            )
        }
    }

    private fun currentDate(): String = LocalDateTime.ofInstant(timeProvider.now(), ZoneId.systemDefault())
        .format(DateTimeFormatter.ISO_DATE)

    fun findReward(userId: UserId): RewardDetails {
        logger.info { "Fetching reward information for user: '${userId.raw}'" }
        val item = repository.findReward(userId)
        if (item == null) {
            throw UserNotFoundException(userId)
        }
        return RewardDetails(
            userId = item.userId,
            spentAmount = item.spentAmount,
            purchaseDayCount = item.purchaseDays.size,
            granted = item.isGranted(rewardVisitor)
        )
    }

    companion object {
        val logger = KotlinLogging.logger { }
    }
}
