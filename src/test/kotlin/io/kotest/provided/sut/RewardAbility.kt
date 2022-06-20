package io.kotest.provided.sut

import io.kotest.assertions.withClue
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import jakarta.inject.Singleton
import tech.zone84.updateoperators.domain.shared.Money
import tech.zone84.updateoperators.domain.shared.UserId
import tech.zone84.updateoperators.infrastructure.rest.RewardPurchasedServiceRequest
import java.time.Instant

@Singleton
class RewardAbility(private val client: ApplicationClient, private val timeProvider: ConfigurableTimeProvider) {
    fun thereIsTime(now: Instant) {
        timeProvider.nowIs(now)
    }

    fun thereIsPurchasedService(userId: UserId, totalAmount: Money) {
        val response = client.postPurchasedService(
            RewardPurchasedServiceRequest(
                userId = userId.raw,
                totalAmount = totalAmount.amount.toPlainString()
            )
        )
        withClue("Response status for new purchased service of user '${userId.raw}'") {
            response.status.shouldBe(HttpStatus.OK)
        }
    }
}
