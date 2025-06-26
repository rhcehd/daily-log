package dev.rhcehd123.dailylog.unit.repository

import dev.rhcehd123.dailylog.data.model.Content
import dev.rhcehd123.dailylog.data.repository.ContentRepository
import dev.rhcehd123.dailylog.data.repository.impl.ContentRepositoryImpl
import dev.rhcehd123.dailylog.unit.repository.fake.FakeContentDao
import dev.rhcehd123.dailylog.utils.DataState
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.mockk
import kotlinx.coroutines.flow.first

class ContentRepositoryImplTest : FunSpec({

    lateinit var fakeDao: FakeContentDao
    lateinit var repository: ContentRepository

    beforeEach {
        fakeDao = FakeContentDao()
        repository = ContentRepositoryImpl(
            fakeDao,
            appDatabase = mockk(relaxed = true)
        )
    }

    test("addContent should return true when content is added successfully") {
        val content = Content(date = 123L, comment = "Content")
        repository.addContent(content) shouldBe true
    }

    test("contentsFlow should emit a empty list with empty dao") {
        val dataState = repository.contentsFlow.first().shouldBeInstanceOf<DataState.Success<List<Content>>>()
        dataState.data shouldHaveSize 0
    }

    test("contentsFlow should emit a list with added content") {
        val content = Content(date = 123L, comment = "Content")
        repository.addContent(content)
        val dataState = repository.contentsFlow.first().shouldBeInstanceOf<DataState.Success<List<Content>>>()
        dataState.data.let {
            it shouldHaveSize 1
            it[0] shouldBe Content(date = 123L, comment = "Content")
        }
    }
})