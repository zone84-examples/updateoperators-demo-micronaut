package tech.zone84.updateoperators

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.provided.sut.ApplicationClient
import io.kotest.provided.sut.RewardAbility
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import tech.zone84.updateoperators.TestConstants.DAY1_MOMENT1
import tech.zone84.updateoperators.TestConstants.DAY1_MOMENT2
import tech.zone84.updateoperators.TestConstants.DAY2_MOMENT1
import tech.zone84.updateoperators.TestConstants.DAY3_MOMENT1
import tech.zone84.updateoperators.TestConstants.SAMPLE_USER_ID
import tech.zone84.updateoperators.domain.shared.Money

@MicronautTest
class ApiSpec(
    private val ability: RewardAbility,
    private val api: ApplicationClient
) : ShouldSpec({
    should("read previously stored reward status") {
        // given
        val amount = Money.from("31.50")
        ability.thereIsTime(DAY1_MOMENT1)
        ability.thereIsPurchasedService(userId = SAMPLE_USER_ID, totalAmount = amount)

        // when
        val response = api.fetchReward(SAMPLE_USER_ID.raw)

        // then
        response.status shouldBe HttpStatus.OK
        response.body.get().let { body ->
            body.userId shouldBe SAMPLE_USER_ID.raw
            body.purchaseDayCount shouldBe 1
            body.spentAmount shouldBe amount.asString()
            body.granted shouldBe false
        }
    }

    should("grant a reward when buying services in 3 different days and spent enough money on them") {
        // given
        val singlePurchaseAmount = Money.from("35.00")
        ability.thereIsTime(DAY1_MOMENT1)
        ability.thereIsPurchasedService(userId = SAMPLE_USER_ID, totalAmount = singlePurchaseAmount)
        ability.thereIsTime(DAY2_MOMENT1)
        ability.thereIsPurchasedService(userId = SAMPLE_USER_ID, totalAmount = singlePurchaseAmount)
        ability.thereIsTime(DAY3_MOMENT1)
        ability.thereIsPurchasedService(userId = SAMPLE_USER_ID, totalAmount = singlePurchaseAmount)

        // when
        val response = api.fetchReward(SAMPLE_USER_ID.raw)

        // then
        response.status shouldBe HttpStatus.OK
        response.body.get().let { body ->
            body.userId shouldBe SAMPLE_USER_ID.raw
            body.purchaseDayCount shouldBe 3
            body.spentAmount shouldBe "105.00"
            body.granted shouldBe true
        }
    }

    should("not grant a reward when buying services in 3 different days, but did not spend enough money") {
        // given
        val singlePurchaseAmount = Money.from("25.00")
        ability.thereIsTime(DAY1_MOMENT1)
        ability.thereIsPurchasedService(userId = SAMPLE_USER_ID, totalAmount = singlePurchaseAmount)
        ability.thereIsTime(DAY2_MOMENT1)
        ability.thereIsPurchasedService(userId = SAMPLE_USER_ID, totalAmount = singlePurchaseAmount)
        ability.thereIsTime(DAY3_MOMENT1)
        ability.thereIsPurchasedService(userId = SAMPLE_USER_ID, totalAmount = singlePurchaseAmount)

        // when
        val response = api.fetchReward(SAMPLE_USER_ID.raw)

        // then
        response.status shouldBe HttpStatus.OK
        response.body.get().let { body ->
            body.userId shouldBe SAMPLE_USER_ID.raw
            body.purchaseDayCount shouldBe 3
            body.spentAmount shouldBe "75.00"
            body.granted shouldBe false
        }
    }

    should("not grant a reward when buying 3 services in only 2 days, and spent enough money on them") {
        // given
        val singlePurchaseAmount = Money.from("35.00")
        ability.thereIsTime(DAY1_MOMENT1)
        ability.thereIsPurchasedService(userId = SAMPLE_USER_ID, totalAmount = singlePurchaseAmount)
        ability.thereIsTime(DAY1_MOMENT2)
        ability.thereIsPurchasedService(userId = SAMPLE_USER_ID, totalAmount = singlePurchaseAmount)
        ability.thereIsTime(DAY2_MOMENT1)
        ability.thereIsPurchasedService(userId = SAMPLE_USER_ID, totalAmount = singlePurchaseAmount)

        // when
        val response = api.fetchReward(SAMPLE_USER_ID.raw)

        // then
        response.status shouldBe HttpStatus.OK
        response.body.get().let { body ->
            body.userId shouldBe SAMPLE_USER_ID.raw
            body.purchaseDayCount shouldBe 2
            body.spentAmount shouldBe "105.00"
            body.granted shouldBe false
        }
    }
})
