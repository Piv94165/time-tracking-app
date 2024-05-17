package com.example.time_tracking_app

import android.content.Context
import androidx.room.Room
import com.example.time_tracking_app.database.AppDatabase

object Database {

    var database: AppDatabase? = null

    fun getInstance(context: Context) = database ?: Room.databaseBuilder(
        context = context, AppDatabase::class.java, "days-database"
    ).build().also { database = it }
}
