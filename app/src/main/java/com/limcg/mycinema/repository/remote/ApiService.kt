package com.limcg.mycinema.repository.remote

import com.haroldadmin.cnradapter.NetworkResponse
import com.limcg.mycinema.repository.entities.Error
import com.limcg.mycinema.repository.entities.Movie
import com.limcg.mycinema.repository.entities.MovieList
import com.limcg.mycinema.utils.ErrorMessage
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/3/discover/movie")
    suspend fun getMovies(
        @Query("api_key") api_key : String,
        @Query("primary_release_date.lte") release_date : String,
        @Query("sort_by") sort_by : String,
        @Query("page") page : Int
    ) : NetworkResponse<MovieList, ErrorMessage>

    @GET("/3/movie/{id}")
    suspend fun getMovieDetail(
            @Path("id") movie_id : String,
            @Query("api_key") api_key : String
    ) : NetworkResponse<Movie, Error>
}