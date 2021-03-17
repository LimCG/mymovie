package com.limcg.mycinema.repository.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.haroldadmin.cnradapter.NetworkResponse
import com.limcg.mycinema.repository.entities.Movie
import com.limcg.mycinema.repository.remote.ApiService
import com.limcg.mycinema.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MovieDataSource constructor(
    private val scope: CoroutineScope,
    private val apiService: ApiService,
    private val api_key : String,
    private val release_date : String,
    private val sort_by : String) : PageKeyedDataSource<Int, Movie>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {

        scope.launch {

            val response = apiService.getMovies(api_key, release_date, sort_by, 1)
            when(response)
            {
                is NetworkResponse.Success -> {

                    val page = response.body.page

                    callback.onResult(response.body.movies,
                        if (page == 1) null else page - 1, page + 1)

                }
                is NetworkResponse.NetworkError -> {
//                    networkSate.postValue(Resource.Error(response.error.message))
                }
                is NetworkResponse.ServerError -> {
//                    networkSate.postValue(Resource.Error(response.body?.error_msg))
                }
                is NetworkResponse.UnknownError -> {
//                    networkSate.postValue(Resource.Error(response.error.message))
                }
            }

        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        scope.launch {

            val response = apiService.getMovies(api_key, release_date, sort_by, params.key)
            when(response)
            {
                is NetworkResponse.Success -> {

                    val page = response.body.page

                    callback.onResult(response.body.movies, page + 1)

                }
                is NetworkResponse.NetworkError -> {

                }
                is NetworkResponse.ServerError -> {

                }
                is NetworkResponse.UnknownError -> {

                }
            }

        }
    }

}