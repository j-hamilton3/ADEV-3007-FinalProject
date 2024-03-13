package com.example.finalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finalproject.ui.theme.FinalProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppScaffold()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("🎮 Free2Play")
                },
                actions = {
                    IconButton(onClick = { /* TO DO: Integrate Profile*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile navigation item."
                        )
                    }
                    IconButton(onClick = { /* TO DO: Integrate Search */ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search navigation item."
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(), // Fill the width of the BottomAppBar
                    horizontalArrangement = Arrangement.SpaceBetween // Space items evenly
                ) {
                    IconButton(modifier = Modifier.weight(1f), onClick = { /* Integrate Categories */ }) {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "Categories navigation item."
                        )
                    }
                    IconButton(modifier = Modifier.weight(1f), onClick = { /* Integrate All Games (Home Page) */ }) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home/all games navigation item."
                        )
                    }
                    IconButton(modifier = Modifier.weight(1f), onClick = { /* Integrate Saved Games Page */ }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Saved games navigation item."
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Game Entry Composable to go here.
            Text(
                text = "Game Entry placeholder."
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinalProjectTheme {
        AppScaffold()
    }
}