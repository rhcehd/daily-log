package dev.rhcehd123.dailylog.data.repository

import dev.rhcehd123.dailylog.data.model.DailyLog
import dev.rhcehd123.dailylog.utils.DataState
import kotlinx.coroutines.flow.Flow

interface DailyLogRepository {
    val dailyLogsFlow: Flow<DataState<List<DailyLog>>>
    suspend fun addDailyLog(taskId: Long, date: Long, comment: String): Boolean
    fun dailyLogsFlow(taskId: Long): Flow<List<DailyLog>>
}