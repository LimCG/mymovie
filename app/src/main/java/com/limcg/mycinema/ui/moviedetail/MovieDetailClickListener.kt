package com.limcg.mycinema.ui.moviedetail

import com.limcg.mycinema.repository.entities.Movie

interface MovieDetailClickListener {

    fun onClickBookTheMovie(movie : Movie)
}