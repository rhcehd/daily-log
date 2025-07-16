package dev.rhcehd123.dailylog.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dev.rhcehd123.dailylog.data.repository.DailyLogRepository
import dev.rhcehd123.dailylog.data.repository.DailyTaskRepository
import dev.rhcehd123.dailylog.data.repository.SettingsRepository
import dev.rhcehd123.dailylog.fake.FakeDailyLogRepository
import dev.rhcehd123.dailylog.fake.FakeDailyTaskRepository
import dev.rhcehd123.dailylog.fake.FakeSettingsRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
object TestModule {
    @Provides
    fun providesDailyTaskRepository(): DailyTaskRepository = FakeDailyTaskRepository()

    @Provides
    fun providesDailyLogRepository(): DailyLogRepository = FakeDailyLogRepository()

    @Provides
    fun providesSettingsRepository():SettingsRepository = FakeSettingsRepository()
}