package com.example.newsapp.persentation.Home


import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.newsapp.data.model.Article
import com.example.newsapp.persentation.state.State
import com.example.newsapp.persentation.Navogation.NavRoutes


//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(navController:NavHostController) {
    val viewModel: NewsViewModel = hiltViewModel()
    val uiState = viewModel.state.collectAsState()

    val searchText = remember {
        mutableStateOf("")
    }
    val drop = remember {
        mutableStateOf(false)
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "NEWS",
            fontSize = 20.sp,
            fontWeight = FontWeight.W900
        )
        Row(
            modifier =Modifier.fillMaxWidth()
        ) {
//            searchBox
            SearchBar(text = searchText.value) {
                searchText.value = it
                if (searchText.value.isEmpty()) {
                    viewModel.getNews(viewModel.defualt)
                } else {
                    viewModel.getNews(searchText.value)
                }
            }
            // DropDownManu
           IconButton(onClick = { drop.value =!drop.value }) {
               Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = null)
               DropDownManu(drop.value) {
                   drop.value = !drop.value
               }
           }

        }
            Spacer(modifier = Modifier.height(10.dp))
        Spacer(modifier = Modifier.height(20.dp))

        when (uiState.value) {
            is State.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text(text = "Loading")
                }
            }

            is State.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Failed")
                    (uiState.value as State.Error).error?.let { Text(text = it) }
                    ElevatedButton(onClick = { viewModel.getNews() }) {
                        Text(text = "Try Again")
                    }
                }
            }

            else -> run {
                val data = (uiState.value as State.Success).data
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (data != null) {
                        items(data.articles) {
                            NewsItems(it) {
                                navController.navigate(NavRoutes.NewsDetails(it))
                            }
                        }
                    }
                }
            }
        }
    }

}



//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun NewsItems(news: Article, onClicke:()->Unit) {

    Box(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary.copy(0.2f))
            .clickable { onClicke.invoke() }

    ) {
        AsyncImage(
            news.urlToImage, contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                ,
            contentScale = ContentScale.Crop,

        )
        Box(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = news.title + "....",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
            Text(
                text = news.publishedAt?:"",
                color = Color.White,
                modifier = Modifier.align(alignment = Alignment.TopEnd)
            )

        }


    }
}

@Composable
fun SearchBar(
    text: String,
    onSearch: (String) -> Unit
) {
   Box(
       modifier = Modifier
           .width(300.dp)
           .height(50.dp)
   ) {
        OutlinedTextField(value = text, onValueChange = { onSearch(it)},
            placeholder = {
                Text(
                    text = "Search",
                    fontWeight = FontWeight.W900
                )

            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )
        Image(
            imageVector = Icons.Outlined.Search, contentDescription = null,
            modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.CenterEnd)
        )
    }

}


@Composable
fun DropDownManu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
) {
    val isDropDownExpended = remember {
        mutableStateOf(expanded)
    }
    val language = listOf("Hindi","English")
    val country = listOf("India","US")
    val countryPosition = remember {
        mutableStateOf(0)
    }
   val languagePosition = remember {
       mutableStateOf(0)
   }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissRequest() },
    ) {
      language.forEachIndexed { index, name ->
          DropdownMenuItem(text = {
              Text(text = name)
          }, onClick ={
              languagePosition.value= index
              isDropDownExpended.value = false
          })
      }
       country.forEachIndexed { index, countryName ->
           DropdownMenuItem(text = {
               Text(text = countryName)
           }, onClick = {
               countryPosition.value =index
               isDropDownExpended.value = false
           })
       }
    }
}


