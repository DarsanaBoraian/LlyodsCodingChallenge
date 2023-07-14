package com.example.mealdb.data.repository

import com.example.mealdb.data.model.MealsModel


interface Repository {

    suspend fun getAllMeals(char: Char): MealsModel

    suspend fun getMealDetails(id: Long): MealsModel
}