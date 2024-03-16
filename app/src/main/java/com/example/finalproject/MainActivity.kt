package com.example.finalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.ui.theme.FinalProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppScaffold(navController = navController)
                }
            }
        }
    }
}

// Navigation Stuff
@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = "home"
) {
    NavHost(navController = navController, startDestination = startDestination){
        composable("profile") { Profile() }
        composable("search") { Search() }
        composable("home") { AllGames() }
        composable("categories") { Categories() }
        composable("favorites") { Favorites() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(navController: NavHostController) {
    val currentDestination = navController.currentBackStackEntryAsState()
    val currentRoute = currentDestination.value?.destination?.route ?: "home"
    var title by remember { mutableStateOf("🎮Free2Play") }

    // Update the title when currentRoute changes
    LaunchedEffect(currentRoute) {
        title = when (currentRoute) {
            "profile" -> "🎮Free2Play - Profile"
            "search" -> "🎮Free2Play - Search"
            "categories" -> "🎮Free2Play - Categories"
            "favorites" -> "🎮Free2Play - Favorites"
            else -> "🎮Free2Play"
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(title)
                },
                actions = {
                    IconButton(onClick = { navController.navigate("profile")}) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile navigation item."
                        )
                    }
                    IconButton(onClick = { navController.navigate("search")  }) {
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
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(modifier = Modifier.weight(1f), onClick = { navController.navigate("categories") }) {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "Categories navigation item."
                        )
                    }
                    IconButton(modifier = Modifier.weight(1f), onClick = { navController.navigate("home") }) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home/all games navigation item."
                        )
                    }
                    IconButton(modifier = Modifier.weight(1f), onClick = { navController.navigate("favorites") }) {
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
        ){
            MyAppNavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController
            )
        }
    }
}

// Using as a placeholder until we get actual data in.
@Composable
fun GameListEntry(/* To add Game object in parameters */ modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(2.dp, Color.Black, RoundedCornerShape(6.dp))
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Call of Duty: Warzone",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 1.dp),
            fontSize = 20.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.thumbnail_example),
                contentDescription = "Call of Duty Warzone title image.",
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp),
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Genre: Shooter"
                )
                Text(
                    text = "Platform: Windows"
                )
                Text(
                    text = "Release: 2020"
                )
            }
        }
    }
}

// Screen where users can view favorite games.
@Composable
fun Favorites() {
    Text(text="Favorites")
}

// Screen where users can view game categories. This will later accept a list of categories from the DB.
@Composable
fun Categories() {
    val categories = listOf("Shooter", "RPG", "Horror", "MMORPG",
        "Rogue-Like", "Open World", "Real Time Strategy", "Survival",
        "Battle-Royale", "Simulation", "Racing", "Fighting") // This will later be accepted in the parameters of Categories()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(categories) { category ->
            CategoryItem(category = category)
        }
    }
}

// A category Item to be used in the Categories screen.
@Composable
fun CategoryItem(category: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = category, style = MaterialTheme.typography.bodyMedium)
    }
}

// Screen where users can search for games.
@Composable
fun Search(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.Center
    ){
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Search by Game Name:") }
        )
        IconButton(onClick = { /* TODO: Add Search function. */ }) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search button."
            )
        }
    }
}

// Screen where users can view their profile.
@Composable
fun Profile() {
    Text(text="Profiles")
}

// Screen where users can view a specific games' details.
@Composable
fun GameDetails() {
    Text(text="Game Details")
}

// Home Screen, this is where all games are displayed.
@Composable
fun AllGames() {
    LazyColumn {
        item{
            // GameListEntry Composable to go here.
            GameListEntry()
            GameListEntry()
            GameListEntry()
            GameListEntry()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinalProjectTheme {

    }
}