package com.example.mysubmissiononecompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.mysubmissiononecompose.ui.theme.MySubmissionOneComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MySubmissionOneComposeTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val foods = remember(context) {
        FoodRepository.getFoods(context)
    }

    val onFoodClick: (Food) -> Unit = { food ->
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_FOOD, food)
        }
        context.startActivity(intent)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                actions = {
                    IconButton(
                        onClick = {
                            val intent = Intent(context, AboutActivity::class.java)
                            context.startActivity(intent)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = stringResource(R.string.about_title),
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
        },
    ) { innerPadding ->
        FoodGrid(
            foods = foods,
            modifier = Modifier.padding(innerPadding),
            onFoodClick = onFoodClick,
        )
    }
}

@Composable
fun FoodGrid(
    foods: List<Food>,
    modifier: Modifier = Modifier,
    onFoodClick: (Food) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = foods,
            key = { it.name },
            itemContent = { food ->
                FoodItem(
                    food = food,
                ) {
                    onFoodClick(food)
                }
            },
        )
    }
}

@Composable
fun FoodItem(
    food: Food,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            MyAsyncImage(
                image = food.imageUrl,
                contentDescription = food.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )

            Text(
                text = food.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
            )

            Text(
                text = food.location,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
            )

            Text(
                text = food.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

private val SampleFood = Food(
    name = "Sample Food",
    location = "Sample Location",
    description = "This is a sample food item.",
    imageUrl = "",
)

@Preview(showBackground = true)
@Composable
fun FoodItemPreview() {
    MySubmissionOneComposeTheme {
        FoodItem(
            food = SampleFood,
        ) {}
    }
}
