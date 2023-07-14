package com.example.mealdb.ui.mealdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealdb.data.model.MealModel
import com.example.mealdb.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailsViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    val mealDetails = MutableStateFlow(MealModel())

    fun loadMealDetails(mealId: Long) {
        viewModelScope.launch {
            mealDetails.value = repository.getMealDetails(mealId).meals?.get(0)!!
        }
    }
}