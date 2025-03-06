package com.example.newsapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.NewsRepository
import com.example.newsapp.data.model.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsAppViewModel : ViewModel() {
    private val repo = NewsRepository()
    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()

    init {
        getHeadlines()
    }

    private fun getHeadlines(country : String = "us") {
        viewModelScope.launch {
            repo.getHeadlines(country).collectLatest {
                if(it.loading == true){
                    _state.value = AppState(loading = true)
                }else if(it.error.isNullOrBlank().not()){
                    _state.value = AppState(error = it.error)
                }else{
                    _state.value = AppState(data = it.data, loading = false)
                }
            }
        }
    }

    fun getEverything(q : String = "") {
        viewModelScope.launch {
            repo.getEverything(q).collectLatest {
                if(it.loading == true){
                    _state.value = AppState(loading = true)
                }else if(it.error.isNullOrBlank().not()){
                    _state.value = AppState(error = it.error)
                }else{
                    _state.value = AppState(data = it.data, loading = false)
                }
            }
        }
    }
}

data class AppState(
    var loading : Boolean ?= false,
    var error : String ?= "",
    var data : ApiResponse ?= null
)