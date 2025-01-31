package com.example.newsapp.persentation.NewsDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.newsapp.data.model.Article
import com.example.newsapp.persentation.Navogation.NavRoutes


@Composable
fun NewsDetailsScreen(navController: NavController, news: Article) {
    NewsDetails(news, navController)
}


@Composable
fun NewsDetails(news: Article, navController: NavController) {


   Box(
     modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary.copy(0.1f))
    ) {

        AsyncImage(
            model = news.urlToImage ?: "", // Ensure a non-null value
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentScale = ContentScale.Crop
        )
        ConstraintLayout(
          modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val (backBtn, topSpace, summary, newContent) = createRefs()

            Spacer(
                modifier = Modifier
                    .height(400.dp)
                    .constrainAs(topSpace) { top.linkTo(parent.top) }
            )
            Image(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
                    .constrainAs(backBtn) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    }
                    .clickable {
                        navController.navigate(NavRoutes.Home){
                            this.popUpTo(NavRoutes.Home)
                        }
                    }
            )
            Box(
                modifier = Modifier
                    .constrainAs(newContent) {
                        top.linkTo(topSpace.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = androidx.constraintlayout.compose.Dimension.wrapContent
                    }
                    .padding(top = 25.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 16.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(0.5f))
                    .fillMaxSize()
                    .padding(
                        vertical = 10.dp,
                        horizontal = 10.dp
                    )
            ) {
                Text(
                    text = news.description?: "",
                    fontSize = 15.sp,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .width(300.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.primary.copy(0.7f))
                    .padding(top = 10.dp, start = 10.dp)
                    .constrainAs(summary) {
                        top.linkTo(topSpace.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom, margin = 90.dp)
                    }
            ) {
                Text(text = news.publishedAt ?: "", fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = news.title ?: "", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = news.author ?: "Unknown Author", fontSize = 10.sp)
           }
        }
    }
}


