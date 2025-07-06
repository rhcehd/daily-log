package dev.rhcehd123.dailylog.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.rhcehd123.dailylog.data.datastore.DataStoreManager
import dev.rhcehd123.dailylog.data.datastore.DataStoreManagerImpl
import dev.rhcehd123.dailylog.data.repository.DailyLogRepository
import dev.rhcehd123.dailylog.data.repository.DailyTaskRepository
import dev.rhcehd123.dailylog.data.repository.SettingsRepository
import dev.rhcehd123.dailylog.data.repository.impl.DailyLogRepositoryImpl
import dev.rhcehd123.dailylog.data.repository.impl.DailyTaskRepositoryImpl
import dev.rhcehd123.dailylog.data.repository.impl.SettingsRepositoryImpl
import dev.rhcehd123.dailylog.data.room.AppDatabase
import dev.rhcehd123.dailylog.data.room.dao.DailyLogDao
import dev.rhcehd123.dailylog.data.room.dao.DailyTaskDao

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                "database"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesDataStoreManager(
        @ApplicationContext context: Context
    ): DataStoreManager {
        return DataStoreManagerImpl(context)
    }

    @Provides
    fun providesDailyLogDao(
        appDatabase: AppDatabase
    ) = appDatabase.dailyLogDao()

    @Provides
    fun providesDailyTaskDao(
        appDatabase: AppDatabase
    ) = appDatabase.dailyTaskDao()

    @Provides
    fun providesDailyLogRepository(
        dailyLogDao: DailyLogDao,
    ): DailyLogRepository {
        return DailyLogRepositoryImpl(dailyLogDao)
    }

    @Provides
    fun providesDailyTaskRepository(
        dailtTaskDao: DailyTaskDao
    ): DailyTaskRepository {
        return DailyTaskRepositoryImpl(dailtTaskDao)
    }

    @Provides
    fun providesSettingsRepository(
        dataStoreManager: DataStoreManager,
    ): SettingsRepository {
        return SettingsRepositoryImpl(dataStoreManager)
    }
}