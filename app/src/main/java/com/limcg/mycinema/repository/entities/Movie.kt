package com.limcg.mycinema.repository.entities

import com.google.gson.annotations.SerializedName

data class Movie(
        @SerializedName("id") val id : String,
        @SerializedName("title") val title : String,
        @SerializedName("backdrop_path") val backdrop_path : String,
        @SerializedName("poster_path") val poster_path : String,
        @SerializedName("popularity") val popularity : Double,
        @SerializedName("genres") val genres : MutableList<Genres>,
        @SerializedName("runtime") val runtime : Int,
        @SerializedName("overview") val overview : String,
        @SerializedName("original_language") val original_language : String,
        @SerializedName("spoken_languages") val spoken_languages : MutableList<MovieLanguage>
)
