package com.example.newsapp.data

import com.example.newsapp.data.apiBuilder.ApiBuilder
import com.example.newsapp.data.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class NewsRepository {
    private val apiInstance = ApiBuilder.retrofitObject()

    fun getHeadlines(country : String = "us") : Flow <ApiState> {
        return flow {
            emit(ApiState(loading = true))
            try {
                val response : ApiResponse = apiInstance.getHeadlines(country = country)
                emit(ApiState(data = response))
            } catch (e: HttpException) {
                emit(ApiState(error = e.localizedMessage))
            }
            catch (e: Exception){
                emit(ApiState(error = e.localizedMessage))
            }
        }
    }

    fun getEverything(q : String = "q") : Flow<ApiState>{
        return flow {
            emit(ApiState(loading = true))
            try {
                val response : ApiResponse = apiInstance.getEverything(q = q)
                emit(ApiState(data = response))
            } catch (e: HttpException) {
                emit(ApiState(error = e.localizedMessage))
            }
            catch (e: Exception){
                emit(ApiState(error = e.localizedMessage))
            }
        }
    }

}

data class ApiState(
    var loading : Boolean ?= false,
    var error : String ?= "",
    var data : ApiResponse?= null
)