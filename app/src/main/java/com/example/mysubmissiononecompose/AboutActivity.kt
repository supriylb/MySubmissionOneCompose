package com.example.mysubmissiononecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mysubmissiononecompose.ui.theme.MySubmissionOneComposeTheme

class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MySubmissionOneComposeTheme {
                AboutScreen(
                    onBackClick = { finish() },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val name = stringResource(R.string.about_name)
    val email = stringResource(R.string.about_email)
    val photo = stringResource(R.string.about_photo)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.about_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.action_back),
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
        AboutContent(
            name = name,
            email = email,
            photo = photo,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
fun AboutContent(
    name: String,
    email: String,
    photo: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically),
    ) {
        MyAsyncImage(
            image = photo,
            contentDescription = name,
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                ),
        )

        Text(
            text = name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        Text(
            text = email,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    MySubmissionOneComposeTheme {
        AboutScreen(
            onBackClick = {},
        )
    }
}
