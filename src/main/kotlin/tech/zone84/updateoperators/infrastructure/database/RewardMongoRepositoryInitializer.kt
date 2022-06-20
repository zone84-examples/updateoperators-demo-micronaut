package tech.zone84.updateoperators.infrastructure.database

import com.mongodb.client.MongoClient
import com.mongodb.client.model.IndexOptions
import com.mongodb.client.model.Indexes
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import jakarta.inject.Singleton
import mu.KotlinLogging
import tech.zone84.updateoperators.infrastructure.InfrastructureConstants.Database.DATABASE_NAME
import tech.zone84.updateoperators.infrastructure.InfrastructureConstants.Database.REWARDS_COLLECTION
import tech.zone84.updateoperators.infrastructure.InfrastructureConstants.Database.USER_ID_FIELD

/**
 * Micronaut 3.5.1 cannot automatically create MongoDB indices yet. In our case, `userId` should be a unique field.
 * We need to create an index on our own at the application startup.
 */
@Singleton
class RewardMongoRepositoryInitializer(private val mongoClient: MongoClient) : ApplicationEventListener<ServerStartupEvent> {
    override fun onApplicationEvent(event: ServerStartupEvent?) {
        logger.info { "Configuring '$REWARDS_COLLECTION' collection..." }
        mongoClient.getDatabase(DATABASE_NAME)
            .getCollection(REWARDS_COLLECTION)
            .createIndex(Indexes.text(USER_ID_FIELD), IndexOptions().unique(true))
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
