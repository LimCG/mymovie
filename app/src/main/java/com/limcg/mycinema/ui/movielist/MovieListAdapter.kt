package com.limcg.mycinema.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.limcg.mycinema.R
import com.limcg.mycinema.databinding.MovieViewholderBinding
import com.limcg.mycinema.repository.entities.Movie

class MovieListAdapter(
        private val listener: MovieItemClickListener
) : PagedListAdapter<Movie, MovieListViewHolder>(MovieComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding : MovieViewholderBinding = DataBindingUtil.inflate(inflater, R.layout.movie_viewholder, parent, false)
        return MovieListViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.bindView(getItem(position), listener)
    }

    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return (oldItem.id == newItem.id && oldItem.backdrop_path == newItem.backdrop_path)
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

}