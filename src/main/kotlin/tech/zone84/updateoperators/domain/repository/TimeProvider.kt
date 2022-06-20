package tech.zone84.updateoperators.domain.repository

import java.time.Instant

interface TimeProvider {
    fun now(): Instant
}
