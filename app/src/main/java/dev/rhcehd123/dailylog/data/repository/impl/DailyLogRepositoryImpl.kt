package dev.rhcehd123.dailylog.data.repository.impl

import dev.rhcehd123.dailylog.data.mapper.toDomain
import dev.rhcehd123.dailylog.data.mapper.toEntity
import dev.rhcehd123.dailylog.data.model.DailyLog
import dev.rhcehd123.dailylog.data.repository.DailyLogRepository
import dev.rhcehd123.dailylog.data.room.dao.DailyLogDao
import dev.rhcehd123.dailylog.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DailyLogRepositoryImpl @Inject constructor(
    private val dailyLogDao: DailyLogDao,
): DailyLogRepository {
    override val dailyLogsFlow: Flow<DataState<List<DailyLog>>> = dailyLogDao
        .getAll()
        .map { list ->
            list.map{
                it.toDomain()
            }
        }
        .map { DataState.Success(it) }

    override suspend fun addDailyLog(taskId: Long, date: Long, comment: String): Boolean {
        return withContext(Dispatchers.IO) {
            val result = dailyLogDao.insert(DailyLog(taskId = taskId, date = date, comment = comment).toEntity())
            result != -1L
        }
    }

    override fun dailyLogsFlow(taskId: Long): Flow<List<DailyLog>> =
        dailyLogDao.getAllByTaskId(taskId)
            .map { list ->
                list.map {
                    it.toDomain()
                }
            }

}