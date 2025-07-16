package dev.rhcehd123.dailylog.unit.repository

import dev.rhcehd123.dailylog.data.repository.DailyTaskRepository
import dev.rhcehd123.dailylog.data.repository.impl.DEFAULT_DAILY_TASK_NAME
import dev.rhcehd123.dailylog.data.repository.impl.DailyTaskRepositoryImpl
import dev.rhcehd123.dailylog.data.room.dao.DailyTaskDao
import dev.rhcehd123.dailylog.unit.repository.fake.FakeDailyTaskDao
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first

class DailyTaskRepositoryImplTest: FunSpec({
    lateinit var repository: DailyTaskRepository
    lateinit var fakeDao: DailyTaskDao

    beforeEach {
        fakeDao = FakeDailyTaskDao()
        repository = DailyTaskRepositoryImpl(fakeDao)
    }

    test("dailyTasksFlow should emit an empty list with empty dao") {
        repository.dailyTasksFlow.first() shouldHaveSize 0
    }

    test("addDailyTask should return expected DailyTask") {
        repository.addDailyTask("Test Task").also {
            it.name shouldBe "Test Task"
        }
    }

    context("DailyTaskDao is empty") {
        test("getFirstOrCreateDailyTask should return expected DailyTask") {
            repository.getFirstOrCreateDailyTask().also {
                it.name shouldBe DEFAULT_DAILY_TASK_NAME
            }
        }
    }

    context("DailyTaskDao is not empty") {
        beforeEach {
            fakeDao = FakeDailyTaskDao(false)
            repository = DailyTaskRepositoryImpl(fakeDao)
        }
        test("getFirstOrCreateDailyTask should return expected DailyTask") {
            repository.getFirstOrCreateDailyTask().also {
                it.name shouldBe FakeDailyTaskDao.DEFAULT_TEST_TASK_NAME
            }
        }
    }
})