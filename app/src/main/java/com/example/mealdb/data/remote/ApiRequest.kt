package com.example.mealdb.data.remote


import com.example.mealdb.data.model.MealsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {

    @GET(ApiDetails.SEARCH_URL)
    suspend fun getMeals(@Query("f") f: String): MealsModel

    //Look up individual meal details
    @GET(ApiDetails.LOOKUP_MEAL)
    suspend fun getMealDetials(@Query("i") mealId: Long): MealsModel
}