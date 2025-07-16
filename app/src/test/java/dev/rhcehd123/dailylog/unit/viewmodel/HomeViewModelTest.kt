package dev.rhcehd123.dailylog.unit.viewmodel

import app.cash.turbine.test
import dev.rhcehd123.dailylog.ProjectConfig
import dev.rhcehd123.dailylog.data.model.DailyLog
import dev.rhcehd123.dailylog.data.model.DailyTask
import dev.rhcehd123.dailylog.data.repository.DailyLogRepository
import dev.rhcehd123.dailylog.data.repository.SettingsRepository
import dev.rhcehd123.dailylog.ui.model.ContentType
import dev.rhcehd123.dailylog.ui.screen.home.HomeUiState
import dev.rhcehd123.dailylog.ui.screen.home.HomeViewModel
import dev.rhcehd123.dailylog.utils.DataState
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest : FunSpec({
    val scope = ProjectConfig.testScope
    lateinit var viewModel: HomeViewModel
    lateinit var dailyLogRepository: DailyLogRepository
    lateinit var settingsRepository: SettingsRepository

    test("uiState should emit Success containing expected dailyTask, dailyLog and contentType") {
        val testDailyTask = DailyTask(id = 1L, name = "Test Task")
        val testDailyLogs = listOf(DailyLog(taskId = 1L, date = 1L, comment = "Hello"))
        val testType = "list"

        dailyLogRepository = mockk {
            every { dailyLogsFlow(any()) } returns flowOf(testDailyLogs)
        }
        settingsRepository = mockk {
            every { contentTypeFlow } returns flowOf(DataState.Success(testType))
        }
        viewModel = HomeViewModel(dailyLogRepository, settingsRepository)
        viewModel.setDailyTask(testDailyTask)
        scope.advanceUntilIdle()

        viewModel.uiState.test {
            val item = awaitItem()
            item shouldBe HomeUiState(
                isLoading = false,
                dailyTask = testDailyTask,
                dailyLogs = testDailyLogs,
                contentType = ContentType.ListType
            )
        }
    }
})