package com.example.mealdb.data.repository

import com.example.mealdb.data.model.MealModel
import com.example.mealdb.data.model.MealsModel
import com.example.mealdb.data.remote.ApiRequest
import org.junit.Before
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import kotlin.random.Random

abstract class MealDbTestBase {
    private val mockedApiRequest = mock<ApiRequest> ()
    val repository = RepositoryImpl(mockedApiRequest)

    @Before
    fun before() {
        mockedApiRequest.stub {
            onBlocking { getMeals(any()) }.thenReturn(getTestMeals())
            onBlocking { getMealDetials(any()) }.thenReturn(getTestMealDetails())
        }
    }

    companion object {
        fun getTestMeals(): MealsModel {
            return MealsModel(mealDetails)
        }

        fun getTestMealDetails(): MealsModel {
            val res = mealDetails.find { et -> et.idMeal.toString() == 1.toString() }
            return res?.let { MealsModel(listOf(it)) } ?: MealsModel(listOf())
        }

        private val categoryTypes =
            listOf<String>("Beef", "Chicken", "Breakfast", "Dessert", "Goat", "Lamb")
        private val ingredientTypes =
            listOf<String>("Chicken", "Salmon", "Beef", "Pork", "Avocado", "Aubergine")
        private val areaTypes =
            listOf<String>("Chinese", "Dutch", "Croatian", "Indian", "American", "Malaysian")
        private val measureTypes = listOf<String>("30 g", " 1 kg", "200 ml")
        private val mealDetails = (1..250).map { id ->
            MealModel(
                strMeal = "Meal Name $id",
                strCategory = randomPick(categoryTypes),
                strArea = randomPick(areaTypes),
                strMealThumb = "",
                strInstructions = "Test Instructions in a paragraph",
                strIngredient1 = randomPick(ingredientTypes),
                strIngredient2 = randomPick(ingredientTypes),
                strMeasure1 = randomPick(measureTypes),
                strMeasure2 = randomPick(measureTypes),
                idMeal = id.toString()
            )
        }.toList()

        private fun <T> randomPick(list: List<T>): T {
            val size = list.size
            val random = Random.nextInt(size - 1)
            return list[random]
        }
    }
}