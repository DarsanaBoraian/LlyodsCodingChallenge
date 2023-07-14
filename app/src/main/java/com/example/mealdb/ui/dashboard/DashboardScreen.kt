package com.example.mealdb.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mealdb.R
import com.example.mealdb.data.model.MealModel
import com.example.mealdb.data.model.MealsModel
import com.example.mealdb.navigation.Screen

@Composable
fun DashboardScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<DashboardViewModel>()
    val mealsList: MealsModel by viewModel.mealsList.collectAsState(initial = MealsModel())

    val navigateToDetails: (String) -> Unit = { mealId ->
        navController.navigate("${Screen.MealDetails.route}/$mealId")
    }
    val loadMore =  { viewModel.loadMoreMeals() }
    DashboardContent(mealsList.meals!!, navigateToDetails, loadMore)
}

@Composable
private fun DashboardContent(
    mealsList: List<MealModel>,
    onNextClick: (String) -> Unit,
    loadMore: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(50.dp))
            Image(
                painter = painterResource(R.drawable.baseline_fastfood_24),
                contentDescription = "Icon",
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Click on items to see details",
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6F7FF7),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier) {
            ListMeals(mealsList, onNextClick, loadMore)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ListMeals(
    mealsList: List<MealModel>,
    onNextClick: (String) -> Unit,
    loadMore: () -> Unit
) {
    //creating a state object, to hold current state(eg. like holder, position) of list
    val listState = rememberLazyListState()

    LazyColumn(state = listState, modifier = Modifier) {
        itemsIndexed(mealsList) { index, meal ->
            Row(
                modifier = Modifier
                    .clickable {
                        onNextClick(meal.idMeal!!)
                    }
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(.65f)
                        .padding(start = 16.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(meal.strMealThumb)
                            .crossfade(true)
                            .build(),
                        contentDescription = meal.strMeal
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1.75f)
                        .padding(start = 12.dp)
                ) {
                    Text(meal.strMeal!!, style = MaterialTheme.typography.h5)
                    Row() {
                        Text(meal.strArea!!, style = MaterialTheme.typography.h6)
                        Spacer(Modifier.width(2.dp))
                        Text(meal.strCategory!!, style = MaterialTheme.typography.h6)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
    WhenListBottomReached(listState, loadMore)
}

@Composable
private fun WhenListBottomReached(
    listState: LazyListState,
    loadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            // Identify the last object in the list displayed in emulator. If none it will be null
            val itemInfo = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            // If null (no list items in the layout)  or if displayed item's index is the last (total count - 1), then loadMore
            // for the decision return derived state true else false
            if (itemInfo?.index == listState.layoutInfo.totalItemsCount - 1) {
                true
            } else false
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect {
                // if should load more, then invoke loadMore
                if (it) loadMore()
            }
    }
}

