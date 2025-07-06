package dev.rhcehd123.dailylog.data.repository

import dev.rhcehd123.dailylog.data.model.DailyTask
import kotlinx.coroutines.flow.Flow

interface DailyTaskRepository {
    //val dailyTasksFlow: Flow<DataState<List<DailyTask>>>
    val dailyTasksFlow: Flow<List<DailyTask>>
    suspend fun addDailyTask(name: String): DailyTask
    suspend fun getFirstOrCreateDailyTask(): DailyTask
}