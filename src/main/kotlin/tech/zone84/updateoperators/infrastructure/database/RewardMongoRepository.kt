package tech.zone84.updateoperators.infrastructure.database

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import jakarta.inject.Singleton
import tech.zone84.updateoperators.infrastructure.InfrastructureConstants.Database.DATABASE_NAME
import tech.zone84.updateoperators.infrastructure.InfrastructureConstants.Database.PURCHASE_DAYS_FIELD
import tech.zone84.updateoperators.infrastructure.InfrastructureConstants.Database.REWARDS_COLLECTION
import tech.zone84.updateoperators.infrastructure.InfrastructureConstants.Database.SPENT_AMOUNT_FIELD
import tech.zone84.updateoperators.infrastructure.InfrastructureConstants.Database.USER_ID_FIELD
import java.math.BigDecimal

/**
 * Although Micronaut supports declarative MongoDB repositories, they are not a mature solution yet (and they
 * actually produce a crash while trying to deserialize some [RewardDocument] instances :)). Therefore, we
 * continue using regular MongoDB client API here.
 */
@Singleton
class RewardMongoRepository(private val mongoClient: MongoClient) {
    fun findByUserId(userId: String): RewardDocument? {
        return collection().find(eq(USER_ID_FIELD, userId)).firstOrNull()
    }

    fun update(userId: String, spentAmountIncrement: BigDecimal, newPurchaseDays: Set<String>): Long {
        val result = collection().updateOne(
            eq(REWARDS_COLLECTION, userId),
            Updates.combine(
                Updates.setOnInsert(USER_ID_FIELD, userId),
                Updates.inc(SPENT_AMOUNT_FIELD, spentAmountIncrement),
                Updates.addEachToSet(PURCHASE_DAYS_FIELD, newPurchaseDays.toList())
            ),
            UpdateOptions().upsert(true)
        )
        return result.matchedCount
    }

    private fun collection(): MongoCollection<RewardDocument> {
        return mongoClient.getDatabase(DATABASE_NAME).getCollection(REWARDS_COLLECTION, RewardDocument::class.java)
    }
}
