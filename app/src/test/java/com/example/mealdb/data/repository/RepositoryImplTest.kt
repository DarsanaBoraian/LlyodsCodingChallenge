package com.example.mealdb.data.repository

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class RepositoryImplTest : MealDbTestBase() {

    @Test
    fun test_getAllMeals() = runBlocking  {
        val meals = repository.getAllMeals('a')
        assertEquals(  250, meals.meals?.size ?: 0)
    }

    @Test
    fun test_getMealDetails() = runBlocking  {
        val meals = repository.getMealDetails(1)
        assertEquals(  1, meals.meals?.size ?: 0)
    }
}