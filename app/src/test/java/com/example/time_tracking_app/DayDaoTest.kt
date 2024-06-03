package com.example.time_tracking_app

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.example.datasource.database.AppDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.time.LocalDate


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P], application = Application::class)
class DayDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dayDao: DayDao
    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dayDao = db.dayDao()
    }

    @Test
    fun testInsertionDayEntity() = runBlocking {
        Assert.assertEquals(0, dayDao.getAllWithoutFlow().size)
        val day = DayEntity(date = LocalDate.of(2024, 1, 5))
        dayDao.insertOrUpdateDate(day)
        Assert.assertEquals(1, dayDao.getAllWithoutFlow().size)
    }

    @Test
    fun testGetDayByDate() = runBlocking {
        Assert.assertEquals(0, dayDao.getAllWithoutFlow().size)
        val day = DayEntity(date = LocalDate.of(2024, 1, 5))
        dayDao.insertOrUpdateDate(day)
        Assert.assertEquals(1, dayDao.getAllWithoutFlow().size)
        val dayByDate = dayDao.findByDate(LocalDate.of(2024, 1, 5))
        dayByDate.test {
            Assert.assertNotNull(awaitItem())
        }
    }

    @Test
    fun testGetDayByDateNotFound() = runBlocking {
        Assert.assertEquals(0, dayDao.getAllWithoutFlow().size)
        val day = DayEntity(date = LocalDate.of(2024, 1, 5))
        dayDao.insertOrUpdateDate(day)
        Assert.assertEquals(1, dayDao.getAllWithoutFlow().size)
        val dayByDate = dayDao.findByDate(LocalDate.of(2024, 1, 8))
        dayByDate.test {
            Assert.assertNull(awaitItem())
        }
    }
}