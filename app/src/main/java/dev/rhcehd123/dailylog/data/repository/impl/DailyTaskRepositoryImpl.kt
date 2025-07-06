package dev.rhcehd123.dailylog.data.repository.impl

import dev.rhcehd123.dailylog.data.mapper.toDomain
import dev.rhcehd123.dailylog.data.mapper.toEntity
import dev.rhcehd123.dailylog.data.model.DailyTask
import dev.rhcehd123.dailylog.data.repository.DailyTaskRepository
import dev.rhcehd123.dailylog.data.room.dao.DailyTaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DailyTaskRepositoryImpl @Inject constructor(
    private val dailyTaskDao: DailyTaskDao
) : DailyTaskRepository {
    override val dailyTasksFlow: Flow<List<DailyTask>> = dailyTaskDao
        .getAll()
        .onEach {
            println(it)
        }
        .map { list ->
            list.map {
                it.toDomain()
            }
        }
        //.map { DataState.Success(it) }

    override suspend fun addDailyTask(name: String): DailyTask {
        return withContext(Dispatchers.IO) {
            val id = dailyTaskDao.insert(DailyTask(name = name).toEntity())
            dailyTaskDao.getById(id)?.toDomain() ?: throw Exception("Failed to create task")
        }
    }

    override suspend fun getFirstOrCreateDailyTask(): DailyTask {
        return withContext(Dispatchers.IO) {
            val dailyTask = dailyTaskDao.getFirst()
            if(dailyTask != null) {
                dailyTask.toDomain()
            } else {
                val newTaskId = dailyTaskDao.insert(DailyTask(name = "New Task").toEntity())
                dailyTaskDao.getById(newTaskId)?.toDomain() ?: throw Exception("Failed to create task")
            }
        }
    }
}