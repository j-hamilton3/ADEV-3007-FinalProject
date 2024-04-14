package com.example.finalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.model.Game
import com.example.finalproject.ui.theme.FinalProjectTheme
import com.example.finalproject.ui.theme.GameUiState
import com.example.finalproject.ui.theme.GameViewModel
import coil.compose.rememberImagePainter
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.finalproject.ui.theme.SignInViewModel
import com.example.finalproject.data.AuthRepository
import com.example.finalproject.data.GameUser
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
                    AppScaffold(
                        navController = navController
                    )
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
    startDestination: String = "profile"
) {
    val gameViewModel: GameViewModel = viewModel()
    val gameUiState = gameViewModel.gameUiState

    val signInViewModel: SignInViewModel = viewModel(factory = SignInViewModel.Factory)
    signInViewModel.navigateOnSignIn = {navController.navigate("home")}

    NavHost(navController = navController, startDestination = startDestination){
        composable("profile") { Profile(signInViewModel) }
        composable("search") { Search() }
        composable("home") { AllGames(navController, gameUiState) }
        composable("categories") { Categories(gameUiState) }
        composable("favorites") { Favorites(navController, gameUiState) }
        composable("gameDetails/{gameId}") { backStackEntry ->
            // Retrieve the gameId from the backStackEntry arguments
            val gameId = backStackEntry.arguments?.getString("gameId")?.toIntOrNull()

            val game = if (gameUiState is GameUiState.Success) {
                gameUiState.games.find { it.id == gameId }
            } else null

            if (game != null) {
                GameDetails(game = game)
            } else {
                Text("Game not found")
            }
        }
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
            "favorites" -> "🎮Free2Play - Favorites♥"
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

@Composable
fun GameListEntry(game : Game, navController: NavHostController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(2.dp, Color.Black, RoundedCornerShape(6.dp))
            .padding(4.dp)
            .clickable { navController.navigate("gameDetails/${game.id}") }, // This routes to the specific game.
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = game.title,
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
                painter = rememberImagePainter(game.thumbnail),
                contentDescription = "${game.title} thumbnail image.",
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
                    text = game.genre
                )
                Text(
                    text = game.platform
                )
                Text(
                    text = game.releaseDate
                )
            }
        }
    }
}

// Screen where users can view favorite games.
@Composable
fun Favorites(navController: NavHostController, gameUiState: GameUiState) {
    when (gameUiState) {
        is GameUiState.Success -> {

            val gamesToShow = gameUiState.games.takeLast(4) // Using as placeholder until favorite functionality.

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(gamesToShow) { game ->
                    GameListEntry(game = game, navController = navController)
                }
            }
        }
        GameUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        GameUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Failed to load favorite games.")
            }
        }
    }
}

// Screen where users can view game categories.
@Composable
fun Categories(gameUiState: GameUiState) {

    val categories = when (gameUiState) {
        is GameUiState.Success -> {
            // Extract the genre from each game and remove duplicates.
            gameUiState.games.map { it.genre.trim() }.distinct().sorted()
        }
        else -> listOf() // Return an empty list if not in Success state.
    }

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
fun Profile(signInViewModel: SignInViewModel) {

    // Firebase Auth data.
    val currentUser: GameUser? = signInViewModel.uiState.value.currentUser
    val uiState by signInViewModel.uiState

    val email = currentUser?.email.toString()
    val favoriteGames = 4 // Placeholder for now...

    val isEmailAndPasswordNotEmpty = uiState.email.isNotEmpty() && uiState.password.isNotEmpty()

    if (currentUser != null) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .width(IntrinsicSize.Max),
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profile Icon.",
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = email,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Favorite Games: $favoriteGames",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical= 32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = uiState.email,
                onValueChange = { emailValue -> signInViewModel.updateEmailState(emailValue) },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            TextField(
                value = uiState.password,
                onValueChange = { passwordValue -> signInViewModel.updatePasswordState(passwordValue) },
                visualTransformation = PasswordVisualTransformation(),
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { signInViewModel.authenticateUser() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    enabled = isEmailAndPasswordNotEmpty
                ) {
                    Text(text = "Login")
                }
                Button(
                    onClick = { signInViewModel.registerUser() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    enabled = isEmailAndPasswordNotEmpty
                ) {
                    Text(text = "Register")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = uiState.uiMessage ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

// Screen where users can view a specific games' details.
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameDetails(game: Game) {
    // Placeholder game details.
    val title = game.title
    val releaseDate = game.releaseDate
    val thumbnail = rememberImagePainter(game.thumbnail)
    val platform = game.platform
    val genre = game.genre
    val developer = game.developer
    val publisher = game.publisher
    val description = game.shortDescription
    val url = game.freetogameProfileUrl

    val context = LocalContext.current // Used for clickable URL functionality.

    val screenshots = listOf( // *** This will have to change, I am needing to query a specific Game, to grab these details?
        R.drawable.call_of_duty_warzone_1,
        R.drawable.call_of_duty_warzone_2,
        R.drawable.call_of_duty_warzone_3
    )
    // To set up Pager count.
    val pagerState = rememberPagerState(
        pageCount = { screenshots.size}
    )

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
            item {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                )
                Image(
                    painter = thumbnail,
                    contentDescription = "$title thumbnail",
                    modifier = Modifier.size(300.dp)
                )
                Text(text = "Release Date: $releaseDate", modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Platform: $platform", modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Genre: $genre", modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Developer: $developer", modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Publisher: $publisher", modifier = Modifier.padding(bottom = 8.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Description:", modifier = Modifier.padding(bottom = 8.dp), style = MaterialTheme.typography.headlineSmall)
                Text(text = description, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Website Link:", modifier = Modifier.padding(bottom = 8.dp), style = MaterialTheme.typography.headlineSmall)
                Text(
                    text = url,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .clickable {
                            // Open URL in the browser
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        },
                    color = Color(0xFF0645AD)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Screenshots:",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))

                // HorizontalPager for displaying screenshots.
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .height(190.dp)
                        .fillMaxWidth()
                ) { page ->
                    Image(
                        painter = painterResource(id = screenshots[page]),
                        contentDescription = "$title screenshot - $page",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                // The super cool circles, to show amount/place of screenshots.
                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp, top = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color = if (pagerState.currentPage == iteration) Color.Black else Color.LightGray
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(14.dp)
                        )
                    }
                }
            }
        }
    }
}

// Home Screen, this is where all games are displayed.
@Composable
fun AllGames(navController: NavHostController, gameUiState: GameUiState) {
    when (gameUiState) {
        is GameUiState.Success -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(gameUiState.games) { game ->
                    GameListEntry(game = game, navController = navController)
                }
            }
        }
        GameUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        GameUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Failed to load games.")
            }
        }
    }
}



