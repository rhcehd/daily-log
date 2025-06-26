package dev.rhcehd123.dailylog.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dev.rhcehd123.dailylog.fake.FakeContentRepository
import dev.rhcehd123.dailylog.fake.FakeSettingsRepository
import dev.rhcehd123.dailylog.data.repository.ContentRepository
import dev.rhcehd123.dailylog.data.repository.SettingsRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
object TestModule {
    @Provides
    fun providesContentRepository(): ContentRepository = FakeContentRepository()

    @Provides
    fun providesSettingsRepository():SettingsRepository = FakeSettingsRepository()
}