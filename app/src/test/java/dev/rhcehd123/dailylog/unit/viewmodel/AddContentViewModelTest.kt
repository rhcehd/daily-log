package dev.rhcehd123.dailylog.unit.viewmodel

import app.cash.turbine.test
import dev.rhcehd123.dailylog.data.repository.ContentRepository
import dev.rhcehd123.dailylog.ui.screen.addcontent.AddContentViewModel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class AddContentViewModelTest: FunSpec({
    lateinit var contentRepository: ContentRepository
    lateinit var viewModel: AddContentViewModel

    beforeEach {
        contentRepository = mockk {
            coEvery { addContent(any()) } returns true
        }
        viewModel = AddContentViewModel(contentRepository)
    }

    test("uiState should emit expected date after calling updateDate") {
        val date = 123L
        viewModel.uiState.test {
            awaitItem()
            viewModel.updateDate(date)
            awaitItem().date shouldBe date
        }
    }

    test("uiState should emit expected comment after calling updateComment") {
        val comment = "comment"
        viewModel.uiState.test {
            awaitItem()
            viewModel.updateComment(comment)
            awaitItem().text shouldBe comment
        }
    }

    test("addContent should call repository") {
        /*val date = 123L
        val comment = "comment"
        viewModel.updateDate(date)
        viewModel.updateComment(comment)*/

        viewModel.addContent()

        coVerify { contentRepository.addContent(any()) }
    }
})