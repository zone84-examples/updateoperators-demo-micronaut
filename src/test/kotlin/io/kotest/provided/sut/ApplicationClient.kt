package io.kotest.provided.sut

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client
import tech.zone84.updateoperators.infrastructure.rest.RewardPurchasedServiceRequest
import tech.zone84.updateoperators.infrastructure.rest.RewardResponse

@Client("/")
interface ApplicationClient {
    @Post("/rewards/purchased-services")
    @Produces(MediaType.APPLICATION_JSON)
    fun postPurchasedService(@Body request: RewardPurchasedServiceRequest): HttpResponse<Void>

    @Get("/rewards/users/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    fun fetchReward(@PathVariable userId: String): HttpResponse<RewardResponse>
}
