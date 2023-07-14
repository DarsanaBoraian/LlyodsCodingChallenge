package com.example.mealdb.data.model


import com.google.gson.annotations.SerializedName

data class MealsModel(
    @SerializedName("meals")
    val meals: List<MealModel>? = listOf()
)