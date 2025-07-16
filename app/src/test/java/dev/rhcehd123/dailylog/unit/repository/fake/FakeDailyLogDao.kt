package dev.rhcehd123.dailylog.unit.repository.fake

import dev.rhcehd123.dailylog.data.room.dao.DailyLogDao
import dev.rhcehd123.dailylog.data.room.model.DailyLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDailyLogDao(): DailyLogDao {
    private val dailyLogs = mutableListOf<DailyLog>()

    override fun insert(dailyLog: DailyLog): Long {
        dailyLogs.add(dailyLog)
        return dailyLog.id
    }

    override fun insertAll(vararg dailyLogs: DailyLog): List<Long> {
        this.dailyLogs.addAll(dailyLogs)
        return dailyLogs.map { it.id }
    }

    override fun getAllByTaskId(taskId: Long): Flow<List<DailyLog>> {
        return flowOf(emptyList())
    }

    override fun getAll(): Flow<List<DailyLog>> {
        return flowOf(dailyLogs)
    }
}