package dev.rhcehd123.dailylog.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object Utils {
    // 스레드세이프 이슈로 SimpleDateFormat에서 변경
    // withZone 없이는 HH:mm 에 대한 데이터만 가지고 있음
    private val dateFormat = DateTimeFormatter
        .ofPattern("yyyy-MM-dd")
        .withZone(ZoneId.systemDefault())

    fun Long?.toDateString(): String {
        return try {
            if (this == null) {
                "null"
            } else {
                dateFormat.format(LocalDate.ofEpochDay(this))
            }
        } catch (e: Exception) {
            e.toString()
        }
    }

    fun Long?.toDayNumber(): Long {
        return try {
            if (this == null) {
                0L
            } else {
                Instant.ofEpochMilli(this)
                    .atZone(ZoneOffset.UTC)
                    .toLocalDate()
                    .toEpochDay()
            }
        } catch (e: Exception) {
            -1L
        }
    }

    fun Long?.toLocalTimeMillis(): Long {
        val zoneOffset = ZoneId.systemDefault().rules.getOffset(Instant.now())
        val offsetMillis = if(zoneOffset.id.contains("+")) {
            zoneOffset.totalSeconds * 1000L
        } else {
            -(zoneOffset.totalSeconds * 1000L)
        }
        return this?.plus(offsetMillis) ?: -1L
    }

    fun currentLocalTimeMillis(): Long {
        val zoneOffset = ZoneId.systemDefault().rules.getOffset(Instant.now())
        val offsetMillis = if(zoneOffset.id.contains("+")) {
            zoneOffset.totalSeconds * 1000L
        } else {
            -(zoneOffset.totalSeconds * 1000L)
        }
        return Instant.now().toEpochMilli() + offsetMillis
    }

}