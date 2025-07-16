package dev.rhcehd123.dailylog.fake

import dev.rhcehd123.dailylog.data.model.DailyTask
import dev.rhcehd123.dailylog.data.repository.DailyTaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDailyTaskRepository: DailyTaskRepository {
    private val dailyTasks = mutableListOf<DailyTask>(
        DailyTask(1, "Fake1"),
        DailyTask(2, "Fake2"),
        DailyTask(3, "Fake3"),
    )
    override val dailyTasksFlow: Flow<List<DailyTask>>
        get() = flowOf(dailyTasks)

    override suspend fun addDailyTask(name: String): DailyTask {
        val dailyTask = DailyTask(4, name)
        dailyTasks.add(dailyTask)
        return dailyTask
    }

    override suspend fun getFirstOrCreateDailyTask(): DailyTask {
        return DailyTask(1, "Fake")
    }

}