package dev.rhcehd123.dailylog

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import dev.rhcehd123.dailylog.component.TestFab
import dev.rhcehd123.dailylog.utils.TestTag
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class DailyLogScaffoldTest {
    @get:Rule
    val composeTestRule = createComposeRule()

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
        val snackbarHostState = SnackbarHostState()
        composeTestRule.setContent {
            DailyLogScaffold(
                snackbarHostState = snackbarHostState
            ) {}
        }
        snackbarHostState.showSnackbar(testMsg)
        composeTestRule.onNodeWithText(testMsg)
            .assertIsDisplayed()

        composeTestRule.waitUntil {
            snackbarHostState.currentSnackbarData == null
        }
    }
}