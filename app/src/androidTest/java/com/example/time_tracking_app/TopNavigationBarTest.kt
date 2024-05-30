package com.example.time_tracking_app

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.example.time_tracking_app.composables.weekPage.WeekPage
import com.example.time_tracking_app.composables.weekPage.WeekPageViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import com.example.time_tracking_app.utils.Convertors
import kotlinx.coroutines.runBlocking
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

// file: app/src/androidTest/java/com/package/com.example.time_tracking_app.MyComposeTest.kt

@HiltAndroidTest
class TopNavigationBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity
    //ex : val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    //@Inject
    lateinit var weekPageViewModel: WeekPageViewModel
    @Inject
    lateinit var useCase: UseCase

    private val convertors = Convertors()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    // Raw Data !!! should not be modified not to break public holidays tests !!!
    private val week = 20
    private val year = 2024
    private val workingHours = "23h15"

    @Before
    fun setup() {
        hiltRule.inject()
        weekPageViewModel = WeekPageViewModel(useCase, convertors = convertors)
    }

    private fun setupForAWeekWithPublicHoliday() {
        runBlocking {
            useCase.getAllDaysOfSpecificWeek(week, year)
        }
        weekPageViewModel.updateSelectedWeekForTestUI(week, year)
        weekPageViewModel.loadPreviousWeek()
        weekPageViewModel.loadNextWeek()
        weekPageViewModel.loadNextWeek()
        weekPageViewModel.updateSelectedWeekForTestUI(week, year)
        composeTestRule.setContent {
            val days = weekPageViewModel.allDaysForWeek().collectAsState(initial = emptyList())
            val selectedWeek by weekPageViewModel.selectedWeek.collectAsState(initial = 0)
            WeekPage(
                allDays = days.value,
                onClickDay = {},
                weekOfYear = selectedWeek,
                year = 2024,
                workingHours = workingHours,
                onPreviewWeekClicked = { weekPageViewModel.loadPreviousWeek() },
                onNextWeekClicked = { weekPageViewModel.loadNextWeek() },
                convertors = Convertors()
            )
        }
    }

    fun setupForToday() {
        val today = LocalDate.now()
        weekPageViewModel.updateSelectedWeekForTestUI(
            today.get(
                WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()
            ), today.year
        )
        composeTestRule.setContent {
            val days = weekPageViewModel.allDaysForWeek().collectAsState(initial = emptyList())
            val selectedWeek by weekPageViewModel.selectedWeek.collectAsState(initial = 0)
            WeekPage(
                allDays = days.value,
                onClickDay = {},
                weekOfYear = selectedWeek,
                year = 2024,
                workingHours = workingHours,
                onPreviewWeekClicked = { weekPageViewModel.loadPreviousWeek() },
                onNextWeekClicked = { weekPageViewModel.loadNextWeek() },
                convertors = Convertors()
            )
        }
    }

    @Test
    fun shouldDisplayCurrentWeek() {
        setupForAWeekWithPublicHoliday()
        composeTestRule.onNodeWithTag("top-navigation-title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("top-navigation-title").assertTextContains(
            context.getString(
                R.string.top_navigation_title,
                year,
                week,
                workingHours
            )
        )
        Thread.sleep(3000)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        val firstDayOfWeekDate = LocalDate.of(year, 1, 1)
            .with(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(), week.toLong())
            .with(firstDayOfWeek)
        composeTestRule.onAllNodesWithTag("day-card-date", true)
            .assertAny(hasText(convertors.convertDateToString(firstDayOfWeekDate)))
    }

    @Test
    fun shouldDisplayPreviousWeekWhenIClickOnLeftArrowButton() {
        setupForAWeekWithPublicHoliday()
        composeTestRule.onNodeWithTag("previous").performClick()
        Thread.sleep(3000)

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.previous_week))
            .assertExists()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.next_week))
            .assertExists()
        composeTestRule.onNodeWithTag("top-navigation-title").assertIsDisplayed()
        Thread.sleep(3000)
        composeTestRule.onNodeWithTag("top-navigation-title").assertTextContains(
            context.getString(
                R.string.top_navigation_title,
                year,
                week - 1,
                workingHours
            )
        )
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        val firstDayOfWeekDate = LocalDate.of(year, 1, 1)
            .with(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(), (week - 1).toLong())
            .with(firstDayOfWeek)
        composeTestRule.onAllNodesWithTag("day-card-date", true)
            .assertAny(hasText(convertors.convertDateToString(firstDayOfWeekDate)))
    }

    @Test
    fun shouldDisplayNextWeekWhenIClickOnRightArrowButton() {
        setupForAWeekWithPublicHoliday()
        composeTestRule.onNodeWithTag("next").performClick()

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.previous_week))
            .assertExists()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.next_week))
            .assertExists()
        composeTestRule.onNodeWithTag("top-navigation-title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("top-navigation-title").assertTextContains(
            context.getString(
                R.string.top_navigation_title,
                year,
                week + 1,
                workingHours
            )
        )
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        val firstDayOfWeekDate = LocalDate.of(year, 1, 1)
            .with(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(), (week + 1).toLong())
            .with(firstDayOfWeek)
        composeTestRule.onAllNodesWithTag("day-card-date", true)
            .assertAny(hasText(convertors.convertDateToString(firstDayOfWeekDate)))
    }

    @Test
    fun shouldDisplayDayEditionBottomSheetWhenIClickOnOnePassedDay() {
        setupForAWeekWithPublicHoliday()
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        val firstDayOfWeekDate = LocalDate.of(year, 1, 1)
            .with(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(), week.toLong())
            .with(firstDayOfWeek)
        composeTestRule.onNodeWithText(convertors.convertDateToString(firstDayOfWeekDate))
            .onParent().onParent().performClick()
        composeTestRule.onNodeWithTag("day-edition-bottom-sheet").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayDayEditionBottomSheetWhenIClickOnToday() {
        setupForToday()
        composeTestRule.onNodeWithText(convertors.convertDateToString(LocalDate.now()))
            .performClick()
        composeTestRule.onNodeWithTag("day-edition-bottom-sheet").assertIsDisplayed()
    }

    @Test
    fun shouldNotDisplayDayEditionBottomSheetWhenIClickOnOneNextDay() {
        setupForToday()
        val today = LocalDate.now()
        val nextDayToClick: LocalDate
        if (today.dayOfWeek != DayOfWeek.FRIDAY) nextDayToClick = today.plusDays(1)
        else {
            composeTestRule.onNodeWithTag("next").performClick()
            nextDayToClick = today.plusDays(3)
        }
        composeTestRule.onNodeWithText(convertors.convertDateToString(nextDayToClick))
            .performClick()
        composeTestRule.onNodeWithTag("day-edition-bottom-sheet").assertDoesNotExist()
    }

    @Test
    fun shouldNotDisplayDayEditionBottomSheetWhenIClickOnOnePublicHoliday() {
        setupForAWeekWithPublicHoliday()
        val publicHoliday = LocalDate.of(year, 5, 8)
        composeTestRule.onNodeWithTag("previous").performClick()
        composeTestRule.onNodeWithText(convertors.convertDateToString(publicHoliday))
            .performClick()
        composeTestRule.onNodeWithTag("day-edition-bottom-sheet").assertDoesNotExist()
    }


}