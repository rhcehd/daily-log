package dev.rhcehd123.dailylog.unit.viewmodel

import app.cash.turbine.test
import dev.rhcehd123.dailylog.ProjectConfig
import dev.rhcehd123.dailylog.data.repository.DailyLogRepository
import dev.rhcehd123.dailylog.ui.screen.addlog.AddLogViewModel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle

@OptIn(ExperimentalCoroutinesApi::class)
class AddLogViewModelTest: FunSpec({
    val scope = ProjectConfig.testScope
    lateinit var dailyLogRepository: DailyLogRepository
    lateinit var viewModel: AddLogViewModel

    beforeEach {
        dailyLogRepository = mockk {
            coEvery { addDailyLog(taskId = any(), date = any(), comment = any()) } returns true
        }
        viewModel = AddLogViewModel(dailyLogRepository)
    }

    test("uiState should emit expected date after calling updateDate") {
        val date = 123L
        viewModel.updateDate(date)
        scope.advanceUntilIdle()
        viewModel.uiState.test {
            awaitItem().date shouldBe date
        }
    }

    test("uiState should emit expected comment after calling updateComment") {
        val comment = "comment"
        viewModel.updateComment(comment)
        scope.advanceUntilIdle()
        viewModel.uiState.test {
            awaitItem().text shouldBe comment
        }
    }

    test("selectedTaskId should emit expected value after calling updateSelectedTaskId") {
        val taskId = 123L
        viewModel.updateSelectedTaskId(taskId)
        scope.advanceUntilIdle()
        viewModel.selectedTaskId.test {
            awaitItem() shouldBe taskId
        }
    }

    test("addContent should call repository") {
        viewModel.addContent()
        coVerify { dailyLogRepository.addDailyLog(taskId = any(), date = any(), comment = any()) }
    }
})