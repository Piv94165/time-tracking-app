package com.example.time_tracking_app

import android.content.Context
import androidx.room.Room
import com.example.time_tracking_app.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun getInstance(
        @ActivityContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context = context, AppDatabase::class.java, "days-database"
        ).build()
    }
}
