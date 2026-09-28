package com.example.mysubmissiononecompose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mysubmissiononecompose.ui.theme.MySubmissionOneComposeTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val food: Food? = intent.getParcelableExtraCompat(EXTRA_FOOD)

        setContent {
            MySubmissionOneComposeTheme {
                DetailScreen(
                    food = food,
                    onBackClick = { finish() },
                    onShareClick = { selectedFood ->
                        shareFood(this, selectedFood)
                    },
                )
            }
        }
    }

    private fun shareFood(context: Context, food: Food) {
        val shareText = "${food.name}\n${food.location}\n\n${food.description}"
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        context.startActivity(
            Intent.createChooser(
                shareIntent,
                context.getString(R.string.title_share_via),
            ),
        )
    }

    companion object {
        const val EXTRA_FOOD = "extra_food"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    food: Food?,
    onBackClick: () -> Unit,
    onShareClick: (Food) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = food?.name ?: stringResource(R.string.app_name),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.action_back),
                        )
                    }
                },
                actions = {
                    if (food != null) {
                        IconButton(onClick = { onShareClick(food) }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = stringResource(R.string.action_share),
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
        },
    ) { innerPadding ->
        if (food != null) {
            DetailContent(
                food = food,
                onShareClick = { onShareClick(food) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            )
        } else {
            EmptyContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            )
        }
    }
}

@Composable
private fun DetailContent(
    food: Food,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        MyAsyncImage(
            image = food.imageUrl,
            contentDescription = food.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(16.dp)),
        )

        Text(
            text = food.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp),
            )

            Text(
                text = food.location,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Text(
            text = food.description,
            style = MaterialTheme.typography.bodyLarge,
        )

        Button(
            onClick = onShareClick,
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
            modifier = Modifier
                .height(56.dp)
                .widthIn(max = 320.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            shape = MaterialTheme.shapes.extraLarge,
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = stringResource(R.string.action_share))
        }
    }
}

@Composable
private fun EmptyContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.msg_data_not_found),
            style = MaterialTheme.typography.bodyLarge,
        )
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
fun DetailScreenPreview() {
    MySubmissionOneComposeTheme {
        DetailScreen(
            food = SampleFood,
            onBackClick = {},
            onShareClick = {},
        )
    }
}
