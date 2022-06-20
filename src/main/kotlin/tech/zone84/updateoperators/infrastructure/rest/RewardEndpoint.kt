package tech.zone84.updateoperators.infrastructure.rest

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.hateoas.Link
import tech.zone84.updateoperators.domain.Domain
import tech.zone84.updateoperators.domain.UserNotFoundException
import tech.zone84.updateoperators.domain.shared.UserId

@Controller("/rewards")
class RewardEndpoint(private val domain: Domain) {
    @Post("/purchased-services", consumes = [MediaType.APPLICATION_JSON])
    fun rewardPurchasedService(@Body request: RewardPurchasedServiceRequest) {
        domain.rewardPurchase(RewardPurchasedServiceMapper.toDomain(request))
    }

    @Get("users/{userId}", produces = [MediaType.APPLICATION_JSON])
    fun findReward(@PathVariable userId: String): RewardResponse {
        return RewardMapper.fromDomain(domain.findReward(UserId.from(userId)))
    }

    @Error
    fun userNotFoundError(request: HttpRequest<*>, exception: UserNotFoundException): HttpResponse<BusinessError> {
        val error = BusinessError(exception.message ?: "")
            .link(Link.SELF, Link.of(request.uri))

        return HttpResponse.status<BusinessError>(HttpStatus.NOT_FOUND, "Not found")
            .body(error)
    }
}
