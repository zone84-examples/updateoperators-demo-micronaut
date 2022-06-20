package tech.zone84.updateoperators.infrastructure.rest

import io.micronaut.http.hateoas.AbstractResource

class BusinessError(val message: String) : AbstractResource<BusinessError>()