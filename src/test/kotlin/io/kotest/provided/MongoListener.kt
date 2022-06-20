package io.kotest.provided

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import io.kotest.core.listeners.BeforeProjectListener
import io.kotest.core.listeners.BeforeTestListener
import io.kotest.core.test.TestCase
import mu.KotlinLogging
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName
import tech.zone84.updateoperators.infrastructure.InfrastructureConstants.Database.DATABASE_NAME
import tech.zone84.updateoperators.infrastructure.InfrastructureConstants.Database.REWARDS_COLLECTION
import java.time.Duration

object MongoListener : BeforeProjectListener, BeforeTestListener {
    private val logger = KotlinLogging.logger { }
    val mongo = MongoDBContainer(DockerImageName.parse("mongo:4.0.10"))
    private lateinit var client: MongoClient

    override suspend fun beforeProject() {
        logger.info { "Starting MongoDB..." }
        mongo.start()
        mongo.waitingFor(
            Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30L))
        )
        logger.info { "Done. Replica set URL: '${mongo.replicaSetUrl}'"}
        client = MongoClients.create(mongo.replicaSetUrl)
    }

    override suspend fun beforeTest(testCase: TestCase) {
        logger.info { "Dropping MongoDB collections before test..." }
        client.getDatabase(DATABASE_NAME)
            .getCollection(REWARDS_COLLECTION)
            .drop()
    }
}
