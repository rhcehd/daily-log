package dev.rhcehd123.dailylog

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.rhcehd123.dailylog.data.repository.DailyTaskRepository
import dev.rhcehd123.dailylog.ui.screen.drawer.DailyLogDrawer
import dev.rhcehd123.dailylog.utils.TestTag
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class DailyLogDrawerTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var fakeDailyTaskRepository: DailyTaskRepository

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun dailyTaskItem_isSelected_correctly() = runTest {
        composeTestRule.setContent {
            DailyLogDrawer(
                onShowSnackbar = {},
                onNavigateToHome = {}
            )
        }
        fakeDailyTaskRepository.dailyTasksFlow.first().forEachIndexed { index, dailyTask ->
            composeTestRule.onNodeWithTag("${TestTag.DailyTaskItem}_$index")
                .performClick()
                .assertIsSelected()
        }
    }

    @Test
    fun addDailyTaskDialog_isDisplayed() {
        composeTestRule.setContent {
            DailyLogDrawer(
                onShowSnackbar = {},
                onNavigateToHome = {}
            )
        }
        composeTestRule.onNodeWithTag(TestTag.AddDailyTaskButton)
            .performClick()

        composeTestRule.onNodeWithTag(TestTag.AddDailyTaskDialog)
            .assertIsDisplayed()
    }
}