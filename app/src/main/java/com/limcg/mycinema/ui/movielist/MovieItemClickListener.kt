package com.limcg.mycinema.ui.movielist

import com.limcg.mycinema.repository.entities.Movie

interface MovieItemClickListener {

    fun onMovieItemClickListener(movie : Movie)
}