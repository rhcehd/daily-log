package dev.rhcehd123.dailylog.data.model

data class DailyLog (
    val id: Long = 0L,
    val taskId: Long,
    val date: Long,
    val comment: String?
)