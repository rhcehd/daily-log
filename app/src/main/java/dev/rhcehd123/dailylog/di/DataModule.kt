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
import dev.rhcehd123.dailylog.data.repository.ContentRepository
import dev.rhcehd123.dailylog.data.repository.SettingsRepository
import dev.rhcehd123.dailylog.data.repository.impl.ContentRepositoryImpl
import dev.rhcehd123.dailylog.data.repository.impl.SettingsRepositoryImpl
import dev.rhcehd123.dailylog.data.room.AppDatabase
import dev.rhcehd123.dailylog.data.room.dao.ContentDao

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
    fun providesContentDao(
        appDatabase: AppDatabase
    ) = appDatabase.contentDao()

    @Provides
    fun providesContentRepository(
        contentDao: ContentDao,
        appDatabase: AppDatabase
    ): ContentRepository {
        return ContentRepositoryImpl(contentDao, appDatabase)
    }

    @Provides
    fun providesDataStoreManager(
        @ApplicationContext context: Context
    ): DataStoreManager {
        return DataStoreManagerImpl(context)
    }

    @Provides
    fun providesSettingsRepository(
        dataStoreManager: DataStoreManager
    ): SettingsRepository {
        return SettingsRepositoryImpl(dataStoreManager)
    }
}