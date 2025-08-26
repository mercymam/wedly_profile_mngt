package org.app.Utility

import jakarta.enterprise.context.ApplicationScoped
import java.time.ZoneOffset
import java.util.*

@ApplicationScoped
class DateUtility {

    fun convertToUTC(date: Date?): Date? {
        if (date == null) {return null}
        val instant = date.toInstant()
        val utcZoned = instant.atZone(ZoneOffset.UTC)
        val utcDate = Date.from(utcZoned.toInstant())
        println("utcdate is $utcDate")
        return utcDate
    }
}