package com.example.newsapp.presentation.screens

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.Source

@Preview(showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailsNews(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    article: Article = Article(
        source = Source(id = "id", name = "name"),
        author = "author",
        title = "Target rolls back DEI initiatives, the latest big company to retreat - CNBC",
        description = "description",
        url = "url",
        urlToImage = "https://image.cnbcfm.com/api/v1/image/108065996-1732139825263-gettyimages-2185805389-dsc_1477_0cwzukdx.jpeg?v=1737723487&w=1920&h=1080",
        publishedAt = "publishedAt",
        content = "Target on Friday said it's rolling back diversity, equity and inclusion programs including some that aim to make its workforce and merchandise better reflect its customers.\\r\\nIn a memo sent to its emp… [+2075 chars]"
    )
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "News",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = article.title.toString(),
                    modifier = modifier.padding(16.dp),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = 1.sp,
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = article.urlToImage,
                        contentDescription = null,
                        placeholder = painterResource(R.drawable.ic_launcher_foreground),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.content.toString(),
                    modifier = modifier.padding(16.dp),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse(article.url))
                        navController.context.startActivity(intent)
                    },
                    modifier = modifier.padding(16.dp)
                ) {
                    Text(text = "Read Full Article")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}