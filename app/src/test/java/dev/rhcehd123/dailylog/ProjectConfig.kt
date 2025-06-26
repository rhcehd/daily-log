@file:OptIn(ExperimentalCoroutinesApi::class)

package dev.rhcehd123.dailylog

import io.kotest.core.config.AbstractProjectConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

class ProjectConfig: AbstractProjectConfig() {
    override val coroutineTestScope: Boolean = true
    override val coroutineDebugProbes: Boolean = true

    //private val testDispatcher = StandardTestDispatcher()
    private val testDispatcher = UnconfinedTestDispatcher()

    override suspend fun beforeProject() {
        super.beforeProject()
        Dispatchers.setMain(testDispatcher)
    }

    override suspend fun afterProject() {
        super.afterProject()
        Dispatchers.resetMain()
    }
}