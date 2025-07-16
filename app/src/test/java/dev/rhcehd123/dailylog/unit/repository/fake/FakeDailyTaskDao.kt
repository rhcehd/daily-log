package dev.rhcehd123.dailylog.unit.repository.fake

import dev.rhcehd123.dailylog.data.room.dao.DailyTaskDao
import dev.rhcehd123.dailylog.data.room.model.DailyTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDailyTaskDao(isEmpty: Boolean = true): DailyTaskDao {
    companion object {
        const val DEFAULT_TEST_TASK_NAME = "Test Task"
    }

    private val dailyTaskSet = mutableSetOf<DailyTask>()

    init {
        if(!isEmpty) {
            dailyTaskSet.add(DailyTask(id = 1L, name = "Test Task"))
        }
    }

    override fun insert(dailyTask: DailyTask): Long {
        val insertedId: Long
        if(dailyTaskSet.any { it.id == dailyTask.id }) {
            val newDailyTask = dailyTask.copy(id = dailyTask.id + 1)
            dailyTaskSet.add(newDailyTask)
            insertedId = newDailyTask.id
        } else {
            dailyTaskSet.add(dailyTask)
            insertedId = dailyTask.id
        }
        return insertedId
    }

    override fun insertAll(vararg dailyTasks: DailyTask): List<Long> {
        dailyTaskSet.addAll(dailyTasks)
        return dailyTasks.map { it.id }
    }

    override fun getById(id: Long): DailyTask? {
        return dailyTaskSet.find { it.id == id }
    }

    override fun getFirst(): DailyTask? {
        return dailyTaskSet.firstOrNull()
    }

    override fun getAll(): Flow<List<DailyTask>> {
        return flowOf(dailyTaskSet.toList())
    }
}