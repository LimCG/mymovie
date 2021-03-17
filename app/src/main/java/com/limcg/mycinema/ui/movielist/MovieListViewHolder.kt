package com.limcg.mycinema.ui.movielist

import androidx.recyclerview.widget.RecyclerView
import com.limcg.mycinema.databinding.MovieViewholderBinding
import com.limcg.mycinema.repository.entities.Movie

class MovieListViewHolder(
    private var viewBinding : MovieViewholderBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bindView(movie : Movie?, listener: MovieItemClickListener) = viewBinding.let {

        it.movieClick = listener
        it.movie = movie
    }
}