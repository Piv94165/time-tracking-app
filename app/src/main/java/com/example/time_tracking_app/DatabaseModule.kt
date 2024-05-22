package com.example.time_tracking_app

import android.content.Context
import androidx.room.Room
import com.example.time_tracking_app.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providesRoomDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context = context, AppDatabase::class.java, "days-database"
        ).build()
    }

    @Provides
    fun providesDayDao(db: AppDatabase)=db.dayDao()

    @Provides
    fun providesPublicHolidaysDao(db: AppDatabase) = db.PublicHolidayDao()
}


