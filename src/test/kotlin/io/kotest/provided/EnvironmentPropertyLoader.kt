package io.kotest.provided

import io.micronaut.context.env.ActiveEnvironment
import io.micronaut.context.env.PropertySource
import io.micronaut.context.env.PropertySourceLoader
import io.micronaut.core.io.ResourceLoader
import mu.KotlinLogging
import java.io.InputStream
import java.util.*

/**
 * Although Micronaut-test supports injecting test properties into the configuration, that approach has some limitations
 * (e.g. it enforces using a certain test isolation mode). The solution below registers a custom Property Source Loader
 * into Micronaut that configures the proper MongoDB connection string with randomized port. It does not have the
 * limitations of the Micronaut Test approach.
 */
class EnvironmentPropertyLoader : PropertySourceLoader {
    override fun load(resourceName: String?, resourceLoader: ResourceLoader?): Optional<PropertySource> {
        if (resourceName == "application") {
            logger.info { "Configuring the framework..." }
            return Optional.of(
                PropertySource.of(
                    mapOf(
                        "mongodb.uri" to "mongodb://localhost:${MongoListener.mongo.firstMappedPort}"
                    )
                )
            )
        }
        return Optional.empty()
    }

    override fun read(name: String?, input: InputStream?): MutableMap<String, Any>? {
        return null
    }

    override fun loadEnv(
        resourceName: String?,
        resourceLoader: ResourceLoader?,
        activeEnvironment: ActiveEnvironment?
    ): Optional<PropertySource> {
        return Optional.empty()
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
