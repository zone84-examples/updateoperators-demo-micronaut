package tech.zone84.updateoperators.infrastructure.config

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import tech.zone84.updateoperators.domain.Domain
import tech.zone84.updateoperators.domain.grant.RewardSettings
import tech.zone84.updateoperators.domain.repository.RewardRepository
import tech.zone84.updateoperators.domain.repository.TimeProvider
import java.time.Clock
import java.time.Instant

@Factory
class DomainFactory {
    @Bean
    fun domain(rewardRepository: RewardRepository, rewardSettings: RewardSettings, timeProvider: TimeProvider) =
        Domain.build(rewardRepository, rewardSettings, timeProvider)

    @Bean
    fun timeProvider() = object : TimeProvider {
        override fun now(): Instant = Clock.systemDefaultZone().instant()
    }
}
