package io.kotest.provided.sut

import io.micronaut.context.annotation.Primary
import jakarta.inject.Singleton
import tech.zone84.updateoperators.domain.repository.TimeProvider
import java.time.Clock
import java.time.Instant

@Singleton
@Primary
class ConfigurableTimeProvider : TimeProvider {
    @field:Volatile
    private var now: Instant = Clock.systemDefaultZone().instant()

    fun nowIs(time: Instant) {
        now = time
    }

    override fun now(): Instant = now
}
