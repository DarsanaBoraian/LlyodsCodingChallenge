package com.example.mealdb.ui.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealdb.data.model.MealsModel
import com.example.mealdb.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    companion object {
      const  val INITIAL_CHAR = 'a'
    }
    //Stateflow has to be initialized with the default/initial value
    private val _mealsList =  MutableStateFlow(MealsModel())
    val mealsList : StateFlow<MealsModel> = _mealsList

    private var currentChar: Char = INITIAL_CHAR

    init {
        viewModelScope.launch { _mealsList.value = repository.getAllMeals(currentChar) }
    }

    fun getCurrentChar()  = currentChar

    fun loadMoreMeals() {
        if (++currentChar <= 'z') {
            viewModelScope.launch {
                val result = repository.getAllMeals(currentChar)
                val newModel = (result.meals ?: ArrayList())

                //when there is no data starting from current char, try again with next char
                if (newModel.isEmpty()) loadMoreMeals()
                else {
                    val current = mealsList.value
                    _mealsList.value = MealsModel(current.meals!! + newModel)
                }

            }
        } else{
            Log.w("Dashboard","No more data left from the repository, processed till ${currentChar-1}" )
        }
    }


}
