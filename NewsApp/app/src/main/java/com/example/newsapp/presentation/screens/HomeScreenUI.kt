package com.example.newsapp.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import com.example.newsapp.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.newsapp.presentation.navigation.routes.CategoryScreen
import com.example.newsapp.presentation.viewmodels.NewsAppViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun HomeScreenUI(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: NewsAppViewModel
) {
    val scrollState = rememberLazyListState()
    val state = viewModel.state.collectAsState()
    val searchTerm = rememberSaveable { mutableStateOf("") }
    val categoryToSearch = rememberSaveable{
        mutableStateOf(
            arrayListOf("business", "entertainment", "general", "health", "Science", "Games", "Technology", "Sports")
        )
    }
    val selectedCategory = rememberSaveable { mutableStateOf("") }

    if(state.value.loading == true){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if(state.value.error.isNullOrBlank().not()) {
        Text(text = state.value.error.toString(), modifier = Modifier.padding(16.dp))
    } else {
        Column(modifier = modifier.fillMaxSize()) {
            Row (
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ){
                OutlinedTextField(
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp),
                    modifier = modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    value = searchTerm.value,
                    onValueChange = { searchTerm.value = it },
                    placeholder = { Text(text = "Search....")},
                    label = { Text(text = "Search")},
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            modifier = modifier.clickable(
                                enabled = searchTerm.value.isBlank().not(),
                                onClick = {viewModel.getEverything(q = searchTerm.value)}
                            )
                        )
                    }
                )
            }
            Spacer(modifier = modifier.height(10.dp))
            LazyRow (
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                state = scrollState
            ){
                items(categoryToSearch.value){
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (it == selectedCategory.value){
                                MaterialTheme.colorScheme.primary
                            }else{
                                MaterialTheme.colorScheme.surfaceVariant
                            }
                        ),
                        modifier = modifier
                            .padding(horizontal = 5.dp)
                            .clickable {
                                viewModel.getEverything(q = it)
                                selectedCategory.value = it
                            }
                    ) {
                        Text(
                            text = it,
                            fontSize = 15.sp,
                            modifier = modifier.padding(10.dp)
                        )
                    }
                }
            }
            val data = state.value.data
            if(data?.articles!!.isEmpty()){
                Text(text = "No Data Found")
            }
            else{
                val articles = data.articles.filter {
                    it.title?.contains("Removed") == false
                }
                LazyColumn(modifier = modifier.fillMaxSize()) {
                    items(articles){
                        Card(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    navController.navigate(CategoryScreen(
                                        author = it.author,
                                        content = it.content,
                                        description = it.description,
                                        publishedAt = it.publishedAt,
                                        id = it.source?.id,
                                        name = it.source?.name,
                                        title = it.title,
                                        url = it.url,
                                        urlToImage = it.urlToImage
                                    ))
                                },
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column (horizontalAlignment = Alignment.CenterHorizontally){
                                if(it.urlToImage.isNullOrBlank()){
                                    Image(
                                        painter = painterResource(R.drawable.ic_launcher_foreground),
                                        contentDescription = null,
                                        modifier = Modifier.size(100.dp)
                                        )
                                } else{
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        AsyncImage(
                                            model = it.urlToImage,
                                            contentDescription = null,
                                            placeholder = painterResource(R.drawable.ic_launcher_foreground),
                                            modifier = modifier
                                                .fillMaxWidth()
                                                .height(200.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                        )
                                    }
                                }
                                Text(
                                    text = it.title.toString(),
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}