package com.app.composedemoapp.ui.theme.Screen


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.app.composedemoapp.R
import com.app.composedemoapp.ui.theme.Grey3
import com.app.composedemoapp.ui.theme.Grey4
import com.app.composedemoapp.ui.theme.Model.RecipeItem
import com.app.composedemoapp.ui.theme.Purple60
import com.app.composedemoapp.ui.theme.RateBadgeColor
import com.app.composedemoapp.ui.theme.Repository.DetailedRecipeResponse
import com.app.composedemoapp.ui.theme.Secondary40
import com.app.composedemoapp.ui.theme.ViewModel.RecipeViewModel
import com.app.composedemoapp.ui.theme.ViewModel.UiState
import com.app.composedemoapp.ui.theme.textColor
@Composable
fun DashboardUI(viewModel: RecipeViewModel = hiltViewModel()) {
//fun DashboardUI(viewModel: RecipeViewModel? = null)) {
    var recipes: List<RecipeItem> = ArrayList()
    var countries: List<String> = ArrayList()
    val recipeTitle by viewModel.recipeTitle.observeAsState("Loading...")

    var searchText by remember { mutableStateOf("") }
    val uiState by viewModel.recipeState.observeAsState(UiState.Loading)
    val uiStateCountries by viewModel.countryList.observeAsState(UiState.Loading)
    val detailedRcipeState by viewModel.recipeTitle.observeAsState(UiState.Loading)

    LaunchedEffect(Unit) {
        viewModel.getRandomeRecipes()
        viewModel.getCountry()
        viewModel.getRecipeTitle()
    }
    when (detailedRcipeState) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> {
            Log.d("TAG","success detailed $detailedRcipeState ")
        }
        is UiState.Error -> Text(
            text = (detailedRcipeState as UiState.Error).message,
            color = Color.Red
        )
    }


    when (uiState) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            Log.d("RecipeScreen", "Recipe loaded")

            recipes = (uiState as UiState.Success<List<RecipeItem>>).data
            Log.d("RecipeScreen", "Recipe List: ${recipes.size}")
        }

        is UiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = (uiState as UiState.Error).message, color = Color.Red, fontSize = 18.sp
                )
            }
        }
    }
    when(uiStateCountries) {
        is UiState.Error ->  Toast.makeText(LocalContext.current, "Not found!", Toast.LENGTH_SHORT).show()
        is UiState.Loading ->

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

        is UiState.Success -> {
            countries = (uiStateCountries as UiState.Success<List<String>>).data
            Log.d("RecipeScreen", "Country loaded")

        }
    }


    Column(
        modifier = Modifier
            .background(color = Color.White)
            .wrapContentSize()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
    ) {
        ShowHeader()

        Spacer(modifier = Modifier.padding(bottom = 10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {

            TextField(
                value = searchText,
                onValueChange = { searchText = it },

                placeholder = { Text("Search recipe", color = Grey3) },
                modifier = Modifier
                    .padding(end = 10.dp)
                    .border(1.dp, Grey3, RoundedCornerShape(14.dp))
                    .weight(1f),
                colors = TextFieldDefaults.colors(

                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,

                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,

                    )
            )

            Box(modifier = Modifier.padding(10.dp)) {
                Image(
                    painterResource(id = R.drawable.filter),
                    contentDescription = "Profile Icon",
                    modifier = Modifier.size(40.dp),
                )
            }
        }


        Spacer(modifier = Modifier.padding(top = 10.dp))
        // Filtered Recipe List
        val filteredRecipes = recipes.filter {
            it.title.contains(searchText, ignoreCase = true)
        }
        ShowCountryList(it = countries)
        ShowRecipes(recipes = filteredRecipes)
    }
}

@Composable
fun ShowHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                stringResource(R.string.hello_gokul),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.padding(bottom = 5.dp))
            Text(
                stringResource(R.string.what_are_you_cooking_today),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Grey3
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .background(Secondary40, shape = RoundedCornerShape(4.dp)),
        ) {
            Image(
                painterResource(id = R.drawable.user_profile),
                contentDescription = stringResource(R.string.profile_icon),
                modifier = Modifier.size(40.dp),
            )
        }


    }}
val countryCuisineMap = mapOf(
    "All" to null,  // No filter applied
    "India" to "Indian",
    "China" to "Chinese",
    "Japan" to "Japanese",
    "United States" to "American",
    "United Kingdom" to "British"
)

@Composable
fun ShowCountryList(it: List<String>,viewModel: RecipeViewModel = hiltViewModel()) {
    var selectedCountry by remember {
        mutableStateOf<String?>("All")
    }

    LazyRow {
        items(it) {

            Box(modifier = Modifier
                .clickable {
                    selectedCountry = it
                    viewModel.getRandomeRecipes(countryCuisineMap[it])
                }
                .background(
                    if (it == selectedCountry) Purple60 else Color.White, RoundedCornerShape(10.dp)
                )) {
                Text(
                    text = it,
                    color = if (it == selectedCountry) Color.White else textColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
                )
            }
        }
    }
}

@Composable
fun ShowRecipes(recipes: List<RecipeItem>) {
    Log.d("TAG", recipes.size.toString())
    Spacer(modifier = Modifier.padding(bottom = 10.dp))
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Ensures 2 items per row
        contentPadding = PaddingValues(1.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp) // Adds spacing between columns
    ) {
        items(recipes) { item ->
            Log.d("TAG1", item.title)
            ShowRecipeItem(item)
        }
    }

}


@Composable
fun ShowRecipeItem(item: RecipeItem) {
    Box(Modifier.padding(top = 40.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth() // Adjust width as needed
                .padding(1.dp), contentAlignment = Alignment.TopCenter
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(),
                colors = CardDefaults.cardColors(
                    containerColor = Grey4
                )
                //elevation = 20.dp

            ) {
                Text(
                    text = item.title,
                    modifier = Modifier
                        .padding(
                            top = 140.dp, bottom = 30.dp, start = 20.dp, end = 20.dp
                        )
                        .align(Alignment.CenterHorizontally)
                        .height(60.dp),
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis

                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    verticalAlignment = Alignment.Bottom // Ensures items align at the bottom
                ) {
                    Box(
                        modifier = Modifier.weight(1f) // Pushes the second Box to the right

                    ) {
                        Column(
                            modifier = Modifier.align(Alignment.BottomStart)
                        ) {
                            Text(
                                text = stringResource(R.string.time),
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = Grey3
                            )
                            Text(
                                text = "15 Min",
                                modifier = Modifier.padding(top = 5.dp),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Color.White),

                        ) {
                        Image(
                            painterResource(id = R.drawable.inactive),
                            contentDescription = stringResource(R.string.filter_icon),
                            modifier = Modifier.size(24.dp),
                        )


                    }
                }
                Spacer(modifier = Modifier.padding(bottom = 10.dp))
            }
            Column {


                Image(
                    painter = rememberAsyncImagePainter(model = item.image),
                    contentDescription = stringResource(R.string.food_image),
                    modifier = Modifier
                        .size(150.dp) // Adjust size as needed
                        .offset(y = (-40).dp) // Moves the image upwards to overlap
                        .clip(CircleShape), // Ensures a circular shape
                    contentScale = ContentScale.Crop
                )

            }

        }
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-30).dp, y = (-10).dp) // Position badge on top-right
                .background(color = RateBadgeColor, shape = RoundedCornerShape(50)) // Yellow color
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = stringResource(R.string.rating),
                    tint = textColor,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "4", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreen() {
    DashboardUI()
}
