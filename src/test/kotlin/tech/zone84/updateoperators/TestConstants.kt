package tech.zone84.updateoperators

import tech.zone84.updateoperators.domain.shared.UserId
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object TestConstants {
    val SAMPLE_USER_ID = UserId.from("1234567")

    val DAY1_MOMENT1 = time("2022-06-19T09:00:00")
    val DAY1_MOMENT2 = time("2022-06-19T14:00:00")
    val DAY2_MOMENT1 = time("2022-06-20T13:00:00")
    val DAY3_MOMENT1 = time("2022-06-21T13:00:00")

    private fun time(time: String) = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME).atZone(ZoneId.systemDefault()).toInstant()
}