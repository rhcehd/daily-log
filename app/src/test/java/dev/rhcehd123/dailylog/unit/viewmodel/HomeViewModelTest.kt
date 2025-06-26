package dev.rhcehd123.dailylog.unit.viewmodel

import app.cash.turbine.test
import dev.rhcehd123.dailylog.data.model.Content
import dev.rhcehd123.dailylog.data.repository.ContentRepository
import dev.rhcehd123.dailylog.data.repository.SettingsRepository
import dev.rhcehd123.dailylog.ui.model.ContentType
import dev.rhcehd123.dailylog.ui.screen.home.HomeUiState
import dev.rhcehd123.dailylog.ui.screen.home.HomeViewModel
import dev.rhcehd123.dailylog.utils.DataState
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf

class HomeViewModelTest : FunSpec({
    lateinit var viewModel: HomeViewModel
    lateinit var contentRepository: ContentRepository
    lateinit var settingsRepository: SettingsRepository

    test("uiState should emit Success containing expected content and content type") {
        val testContents = listOf(Content(date = 1L, comment = "Hello"))
        val testType = "list"

        contentRepository = mockk {
            every { contentsFlow } returns flowOf(DataState.Success(testContents))
        }
        settingsRepository = mockk {
            every { contentTypeFlow } returns flowOf(DataState.Success(testType))
        }
        viewModel = HomeViewModel(contentRepository, settingsRepository)

        viewModel.uiState.test {
            //awaitItem() shouldBe MainUiState(isLoading = true)
            awaitItem() shouldBe HomeUiState(
                isLoading = false,
                contents = testContents,
                contentType = ContentType.ListType
            )
            cancelAndConsumeRemainingEvents()
        }
    }
})