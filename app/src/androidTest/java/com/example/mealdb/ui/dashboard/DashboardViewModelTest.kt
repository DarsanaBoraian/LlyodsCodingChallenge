package com.example.mealdb.ui.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mealdb.CoroutineDispatcherRule
import com.example.mealdb.repository.MockRepository
import com.example.mealdb.ui.dashboard.DashboardViewModel.Companion.INITIAL_CHAR
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class DashboardViewModelTest {

    private val viewModel = DashboardViewModel(MockRepository())

    @get:Rule
    val coroutineDispatcherRule = CoroutineDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun test_loadMealDetails() {
        runTest {
            advanceUntilIdle()
        }
        assertEquals(INITIAL_CHAR, viewModel.getCurrentChar())
        val uniqueFirstChars = viewModel.mealsList.value.meals!!.map { entry -> entry.strMeal!![0] }.distinct()
        assertEquals(1, uniqueFirstChars.size)
        assertEquals(INITIAL_CHAR, uniqueFirstChars[0])

    }

}