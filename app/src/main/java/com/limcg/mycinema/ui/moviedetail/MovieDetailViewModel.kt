package com.limcg.mycinema.ui.moviedetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.limcg.mycinema.repository.entities.Movie
import com.limcg.mycinema.repository.remote.ApiService
import com.limcg.mycinema.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
        private val apiService: ApiService,
        @Named("api_key") private val api_key : String
) : ViewModel() {

    fun getMovieDetail(movie_id : String) : LiveData<Resource<Movie>> = liveData<Resource<Movie>> {

        emit(Resource.Loading())

        val response = apiService.getMovieDetail(movie_id, api_key)

        when(response)
        {
            is NetworkResponse.Success -> {
                Log.e("TAG", "Success")
                emit(Resource.Success(response.body))
            }
            is NetworkResponse.NetworkError -> {
                Log.e("TAG", "NetworkError")
                emit(Resource.Error(response.error.message))
            }
            is NetworkResponse.ServerError -> {
                Log.e("TAG", "ServerError")
                emit(Resource.Error(response.body?.status_message))
            }
            is NetworkResponse.UnknownError -> {
                Log.e("TAG", "UnknownError")
                emit(Resource.Error(response.error.message))
            }
        }

    }

}