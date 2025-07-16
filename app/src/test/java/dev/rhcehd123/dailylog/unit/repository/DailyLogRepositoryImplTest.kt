package dev.rhcehd123.dailylog.unit.repository

import dev.rhcehd123.dailylog.data.model.DailyLog
import dev.rhcehd123.dailylog.data.repository.DailyLogRepository
import dev.rhcehd123.dailylog.data.repository.impl.DailyLogRepositoryImpl
import dev.rhcehd123.dailylog.unit.repository.fake.FakeDailyLogDao
import dev.rhcehd123.dailylog.utils.DataState
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.flow.first

class DailyLogRepositoryImplTest : FunSpec({

    lateinit var fakeDao: FakeDailyLogDao
    lateinit var repository: DailyLogRepository

    beforeEach {
        fakeDao = FakeDailyLogDao()
        repository = DailyLogRepositoryImpl(fakeDao)
    }

    test("addContent should return true when content is added successfully") {
        repository.addDailyLog(taskId = 0, date = 123L, comment = "Content") shouldBe true
    }

    test("contentsFlow should emit an empty list with empty dao") {
        val dataState = repository.dailyLogsFlow.first().shouldBeInstanceOf<DataState.Success<List<DailyLog>>>()
        dataState.data shouldHaveSize 0
    }

    test("contentsFlow should emit a list with added content") {
        repository.addDailyLog(taskId = 0, date = 123L, comment = "Content")
        val dataState = repository.dailyLogsFlow.first().shouldBeInstanceOf<DataState.Success<List<DailyLog>>>()
        dataState.data.let {
            it shouldHaveSize 1
            it[0] shouldBe DailyLog(taskId = 0, date = 123L, comment = "Content")
        }
    }
})