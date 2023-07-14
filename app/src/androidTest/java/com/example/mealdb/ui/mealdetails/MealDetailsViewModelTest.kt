package com.example.mealdb.ui.mealdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mealdb.CoroutineDispatcherRule

import com.example.mealdb.repository.MockRepository
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MealDetailsViewModelTest  {
    private val repository = MockRepository()

    //Use the mocked repository from test base to initialize the ViewModel
    private val viewModel = MealDetailsViewModel(repository)

    @get:Rule
    val coroutineDispatcherRule = CoroutineDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun test_loadMealDetails() {
        runTest {
            viewModel.loadMealDetails(1)
            advanceUntilIdle()
        }
        assertEquals("1", viewModel.mealDetails.value.idMeal)
    }
}