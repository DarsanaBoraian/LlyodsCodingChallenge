package com.example.mealdb.data.repository

import com.example.mealdb.data.remote.ApiRequest
import com.example.mealdb.data.model.MealsModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiRequest: ApiRequest
) : Repository {

    override suspend fun getAllMeals(char: Char): MealsModel {
        return apiRequest.getMeals(char.toString())
    }


    override suspend fun getMealDetails(id: Long): MealsModel {
        return apiRequest.getMealDetials(id)
    }
}