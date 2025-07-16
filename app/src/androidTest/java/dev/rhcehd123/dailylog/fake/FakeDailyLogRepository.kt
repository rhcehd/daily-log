package dev.rhcehd123.dailylog.fake

import dev.rhcehd123.dailylog.data.model.DailyLog
import dev.rhcehd123.dailylog.data.repository.DailyLogRepository
import dev.rhcehd123.dailylog.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

class FakeDailyLogRepository(var isEmpty: Boolean = false): DailyLogRepository {

    override val dailyLogsFlow: Flow<DataState<List<DailyLog>>>
        get() = if(isEmpty) {
            emptyFlow()
        } else {
            flowOf(DataState.Success(emptyList()))
        }

    override suspend fun addDailyLog(taskId: Long, date: Long, comment: String): Boolean {
        return true
    }

    override fun dailyLogsFlow(taskId: Long): Flow<List<DailyLog>> {
        return if(isEmpty) {
            emptyFlow()
        } else {
            flowOf(emptyList())
        }
    }
}