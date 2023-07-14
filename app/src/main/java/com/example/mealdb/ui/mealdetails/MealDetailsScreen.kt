package com.example.mealdb.ui.mealdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mealdb.data.model.IngredientMeasure
import com.example.mealdb.data.model.MealModel

@Composable
fun MealDetailsScreen(
    mealId: Long
) {
    val mealDetailsModel = hiltViewModel<MealDetailsViewModel>()
    val mealDetails: MealModel by mealDetailsModel.mealDetails.collectAsState(initial = MealModel())

    mealDetailsModel.loadMealDetails(mealId)

    val scrollState = rememberScrollState()
    Surface(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            MealDetailsContent(mealDetails = mealDetails)
        }
    }
}

@Composable
fun MealDetailsContent(mealDetails: MealModel) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            color = Color(0xFF6F7FF7),
            text = mealDetails.strMeal!!,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = mealDetails.strMealThumb,
            contentDescription = "My Image",
            modifier = Modifier
                .size(175.dp)
                .align(Alignment.CenterVertically)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .weight(.75f)
                .padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
                .background(
                    color = Color(0xFF6F7FF7),
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalArrangement = Arrangement.Center
        ) {
            mealDetails.strArea?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(.75f)
                .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
                .background(
                    color = Color(0xFF6F7FF7),
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalArrangement = Arrangement.Center
        ) {
            mealDetails.strCategory?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }


    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(start = 15.dp, bottom = 20.dp, top = 5.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.h6,
            color = Color(0xFF6F7FF7),
            fontWeight = FontWeight.Bold
        )
    }

    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        IngredientsList(mealIngredients = populateIngredients(mealDetails))
    }

    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(start = 15.dp, bottom = 5.dp, top = 15.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Instructions",
            style = MaterialTheme.typography.h6,
            color = Color(0xFF6F7FF7),
            fontWeight = FontWeight.Bold
        )
    }

    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(bottom = 20.dp, start = 15.dp, end = 15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        mealDetails.strInstructions?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.caption
            )
        }
    }


}


@Composable
fun IngredientsList(
    mealIngredients: List<IngredientMeasure>,
    modifier: Modifier = Modifier
) {

    LazyColumn(modifier = modifier.height(180.dp)) {
        itemsIndexed(mealIngredients) { item, ing ->
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = ing.ingredientName,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = ing.measure,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }

}

private fun populateIngredients(model: MealModel): List<IngredientMeasure> {
    val ingNames = listOf(
        model.strIngredient1, model.strIngredient2, model.strIngredient3,
        model.strIngredient4, model.strIngredient5, model.strIngredient6,
        model.strIngredient7, model.strIngredient8, model.strIngredient9,
        model.strIngredient10, model.strIngredient11, model.strIngredient12,
        model.strIngredient13, model.strIngredient14, model.strIngredient15
    )

    val measure = listOf(
        model.strMeasure1, model.strMeasure2, model.strMeasure3,
        model.strMeasure4, model.strMeasure5, model.strMeasure6,
        model.strMeasure7, model.strMeasure8, model.strMeasure9,
        model.strMeasure10, model.strMeasure11, model.strMeasure12,
        model.strMeasure13, model.strMeasure14, model.strMeasure15
    )

    return ingNames.zip(measure).filter { entry -> !entry.first.isNullOrBlank() }
        .map { entry -> IngredientMeasure(entry.first!!, entry.second ?: "") }
}

@Preview
@Composable
fun MealsDetailsContentPreview() {
    MealDetailsContent(mealDetails = MealModel())
}