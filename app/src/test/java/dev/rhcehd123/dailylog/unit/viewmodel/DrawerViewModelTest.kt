package dev.rhcehd123.dailylog.unit.viewmodel

import app.cash.turbine.test
import dev.rhcehd123.dailylog.ProjectConfig
import dev.rhcehd123.dailylog.data.model.DailyTask
import dev.rhcehd123.dailylog.data.repository.DailyTaskRepository
import dev.rhcehd123.dailylog.ui.screen.drawer.DrawerUiState
import dev.rhcehd123.dailylog.ui.screen.drawer.DrawerViewModel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle

@OptIn(ExperimentalCoroutinesApi::class)
class DrawerViewModelTest: FunSpec({
    val scope = ProjectConfig.testScope
    lateinit var dailyTaskRepository: DailyTaskRepository
    lateinit var viewModel: DrawerViewModel

    val testTask = DailyTask(id = 1L, name = "Test Task")

    beforeEach {
        dailyTaskRepository = mockk {
            every { dailyTasksFlow } returns flowOf(listOf(testTask))
            coEvery { getFirstOrCreateDailyTask() } returns testTask
            coEvery { addDailyTask(any()) } returns testTask
        }
        viewModel = DrawerViewModel(dailyTaskRepository)
    }

    test("uiState should emit expected dailyTasks") {
        scope.advanceUntilIdle()
        viewModel.uiState.test {
            awaitItem() shouldBe DrawerUiState(
                isLoading = false,
                dailyTasks = listOf(testTask),
                selectedTaskId = testTask.id
            )
        }
    }

    test("selectedTask should emit expected value after calling updateSelectedTask") {
        viewModel.updateSelectedTask(testTask)
        scope.advanceUntilIdle()
        viewModel.selectedTask.test {
            awaitItem() shouldBe testTask
        }
    }

    test("onAddDailyTask should call repository") {
        viewModel.onAddDailyTask("Test Task")
        coVerify { dailyTaskRepository.addDailyTask(any()) }
    }
})