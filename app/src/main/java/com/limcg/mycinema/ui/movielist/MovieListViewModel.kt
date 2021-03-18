package com.limcg.mycinema.ui.movielist

import androidx.lifecycle.*
import androidx.paging.*
import com.limcg.mycinema.repository.entities.Movie
import com.limcg.mycinema.repository.paging.MovieDataSource
import com.limcg.mycinema.repository.remote.ApiService
import com.limcg.mycinema.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MovieListViewModel @Inject constructor(
        private val apiService: ApiService,
        @Named("api_key") private val api_key : String
) : ViewModel() {

    // Data binding to spinner
    val spinnerTitle = arrayListOf("Ordered by release date",
        "Ordered by alphabetical", "Ordered by rating")
    val spinnerDataList = arrayListOf("release_date.desc", "alphabetical.desc", "rating.desc")

    var progressState : LiveData<Resource<Any>> = MutableLiveData()
    val moveDataSource : MutableLiveData<MovieDataSource> = MutableLiveData<MovieDataSource>()

    init {
        progressState = Transformations.switchMap(moveDataSource, MovieDataSource::progressState)
    }

    val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(10)
            .setEnablePlaceholders(true)
            .build()

    fun getMovieList(
            release_date : String,
            sort_by : String) : LiveData<PagedList<Movie>> {

        val dataSourceFactory = object : DataSource.Factory<Int, Movie>() {

            override fun create(): MovieDataSource {
                val source = MovieDataSource(viewModelScope, apiService, api_key, release_date, sort_by)
                moveDataSource.postValue(source)
                return source
            }
        }

        return LivePagedListBuilder(dataSourceFactory, config).build()
    }

    fun invalidate()
    {
        moveDataSource.value?.invalidate()
    }

}