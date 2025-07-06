package dev.rhcehd123.dailylog.data.mapper

import dev.rhcehd123.dailylog.data.model.DailyLog as DomainDailyLog
import dev.rhcehd123.dailylog.data.room.model.DailyLog as EntityDailyLog
import dev.rhcehd123.dailylog.data.model.DailyTask as DomainDailyTask
import dev.rhcehd123.dailylog.data.room.model.DailyTask as EntityDailyTask

fun DomainDailyLog.toEntity(): EntityDailyLog {
    return EntityDailyLog(
        id = 0,
        taskId = taskId,
        date = date,
        comment = comment
    )
}

fun EntityDailyLog.toDomain(): DomainDailyLog {
    return DomainDailyLog(
        id = id,
        taskId = taskId,
        date = date,
        comment = comment ?: ""
    )
}

fun DomainDailyTask.toEntity(): EntityDailyTask {
    return EntityDailyTask(
        id = 0,
        name = name
    )
}

fun EntityDailyTask.toDomain(): DomainDailyTask {
    return DomainDailyTask(
        id = id,
        name = name
    )
}
