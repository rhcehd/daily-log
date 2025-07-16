package dev.rhcehd123.dailylog.unit.viewmodel

import app.cash.turbine.test
import dev.rhcehd123.dailylog.ProjectConfig
import dev.rhcehd123.dailylog.data.repository.SettingsRepository
import dev.rhcehd123.dailylog.ui.model.ContentType
import dev.rhcehd123.dailylog.ui.screen.settings.SettingsViewModel
import dev.rhcehd123.dailylog.utils.DataState
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
class SettingsViewModelTest: FunSpec({
    val scope = ProjectConfig.testScope
    lateinit var viewModel: SettingsViewModel
    lateinit var settingsRepository: SettingsRepository

    beforeEach {
        settingsRepository = mockk {
            every { contentTypeFlow } returns flowOf(DataState.Success("calendar"))
            coEvery { saveContentType(any()) } returns Unit
        }
        viewModel = SettingsViewModel(settingsRepository)
    }

    context("contentType is empty") {
        beforeEach {
            settingsRepository = mockk {
                every { contentTypeFlow } returns flowOf(DataState.Success(""))
            }
            viewModel = SettingsViewModel(settingsRepository)
        }
        test("uiState should emit Calendar as the default content type") {
            scope.advanceUntilIdle()
            viewModel.uiState.test {
                awaitItem().contentType shouldBe ContentType.CalendarType
            }
        }
    }

    context("contentType is not empty") {
        beforeEach {
            settingsRepository = mockk{
                every { contentTypeFlow } returns flowOf(DataState.Success("list"))
            }
            viewModel = SettingsViewModel(settingsRepository)
        }
        test("uiState should emit expected content type") {
            scope.advanceUntilIdle()
            viewModel.uiState.test {
                awaitItem().contentType shouldBe ContentType.ListType
            }
        }
    }

    test("updateContentType should call repository") {
        viewModel.updateContentType(ContentType.CalendarType)
        coVerify { settingsRepository.saveContentType(any()) }
    }
})