package tech.zone84.updateoperators.domain

import tech.zone84.updateoperators.domain.shared.UserId

class UserNotFoundException(val userId: UserId) : RuntimeException("The specified user has not been found")
