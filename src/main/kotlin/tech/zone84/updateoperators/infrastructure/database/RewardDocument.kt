package tech.zone84.updateoperators.infrastructure.database

import io.micronaut.data.annotation.AutoPopulated
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import org.bson.BsonType
import org.bson.codecs.pojo.annotations.BsonRepresentation
import org.bson.types.ObjectId
import java.math.BigDecimal

@MappedEntity("rewards")
data class RewardDocument(
    @field:Id
    @field:AutoPopulated
    var id: ObjectId,
    @field:BsonRepresentation(BsonType.STRING)
    var userId: String,
    var spentAmount: BigDecimal,
    var purchaseDays: Set<String>
)
