package dev.rhcehd123.dailylog

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.rhcehd123.dailylog.component.TestFab
import dev.rhcehd123.dailylog.ui.component.DailyLogScaffold
import dev.rhcehd123.dailylog.utils.TestTag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DailyLogScaffoldTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Test
    fun testFab_isDisplayed() {
        composeTestRule.setContent {
            DailyLogScaffold(
                fabSlot = { TestFab() }
            ) {}
        }
        composeTestRule.onNodeWithTag(TestTag.TestFab)
            .assertIsDisplayed()
    }

    @Test
    fun settingsAction_isDisplayed() {
        composeTestRule.setContent {
            DailyLogScaffold {}
        }
        composeTestRule.onNodeWithTag(TestTag.SettingsButton)
            .assertIsDisplayed()
    }

    @Test
    fun snackbar_isDisplayed() = runTest {
        val testMsg = "test"
        lateinit var scope: CoroutineScope
        lateinit var snackbarHostState: SnackbarHostState
        composeTestRule.setContent {
            scope = rememberCoroutineScope()
            snackbarHostState = remember { SnackbarHostState() }
            DailyLogScaffold(
                snackbarHostState = snackbarHostState
            ) {}
        }

        composeTestRule.runOnIdle {
            scope.launch {
                try {
                    snackbarHostState.showSnackbar(testMsg)
                } catch (e: Exception) {
                    println("EXCEPTION_OCCURRED")
                    e.printStackTrace()
                }
            }
        }
        composeTestRule.mainClock.advanceTimeBy(300L)

        composeTestRule.onNodeWithText(testMsg)
            .assertIsDisplayed()
    }

    @Test
    fun drawer_isDisplayed() = runTest {
        // monotonicFrameClock 확인
        composeTestRule.setContent {
            DailyLogScaffold(
                scope = rememberCoroutineScope()
            ){}
        }
        composeTestRule.onNodeWithTag(TestTag.NavigationIcon)
            .performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestTag.DrawerContent)
            .assertIsDisplayed()
    }

}