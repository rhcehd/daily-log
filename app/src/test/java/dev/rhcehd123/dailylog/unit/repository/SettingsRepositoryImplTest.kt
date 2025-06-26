package dev.rhcehd123.dailylog.unit.repository

import dev.rhcehd123.dailylog.data.datastore.DataStoreManager
import dev.rhcehd123.dailylog.data.repository.SettingsRepository
import dev.rhcehd123.dailylog.data.repository.impl.SettingsRepositoryImpl
import dev.rhcehd123.dailylog.unit.repository.fake.FakeDataStoreManager
import dev.rhcehd123.dailylog.utils.DataState
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.flow.first

class SettingsRepositoryImplTest: FunSpec({
    lateinit var repository: SettingsRepository
    lateinit var dataStoreManager: DataStoreManager

    beforeEach {
        dataStoreManager = FakeDataStoreManager()
        repository = SettingsRepositoryImpl(dataStoreManager)
    }

    test("contentTypeFlow should emit an empty string with empty datastore") {
        val emitted = repository.contentTypeFlow.first()
        val dataState = emitted.shouldBeInstanceOf<DataState.Success<String>>()
        dataState.data shouldBe ""
    }

    test("contentTypeFlow should emit the saved content type") {
        val contentType = "ContentType"
        repository.saveContentType(contentType)

        val emitted = repository.contentTypeFlow.first()
        val dataState = emitted.shouldBeInstanceOf<DataState.Success<String>>()
        dataState.data shouldBe contentType
    }
})