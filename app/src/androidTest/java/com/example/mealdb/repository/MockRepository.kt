package com.example.mealdb.repository

import com.example.mealdb.data.model.MealModel
import com.example.mealdb.data.model.MealsModel
import com.example.mealdb.data.repository.Repository

import kotlin.random.Random


class MockRepository() : Repository {

    companion object {
        private val alphabets = ('a'..'z').toList()
        private val categoryTypes =
            listOf<String>("Beef", "Chicken", "Breakfast", "Dessert", "Goat", "Lamb")
        private val ingredientTypes =
            listOf<String>("Chicken", "Salmon", "Beef", "Pork", "Avocado", "Aubergine")
        private val areaTypes =
            listOf<String>("Chinese", "Dutch", "Croatian", "Indian", "American", "Malaysian")
        private val measureTypes = listOf<String>("30 g", " 1 kg", "200 ml")
        private val mealDetails = (1..250).map { id ->
            MealModel(
                strMeal = "${alphabets[id % alphabets.size]} Meal Name $id",
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

    override suspend fun getAllMeals(char: Char): MealsModel {
        return MealsModel(mealDetails.filter { entry -> entry.strMeal!!.startsWith(char, true) })
    }

    override suspend fun getMealDetails(id: Long): MealsModel {
        val res = mealDetails.find { et -> et.idMeal.toString() == id.toString() }
        return res?.let { MealsModel(listOf(it)) } ?: MealsModel(listOf())
    }
}