package com.limcg.mycinema.ui.movielist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.limcg.mycinema.R
import com.limcg.mycinema.databinding.ActivityMovieListBinding
import com.limcg.mycinema.repository.entities.Movie
import com.limcg.mycinema.ui.moviedetail.MovieDetailActivity
import com.limcg.mycinema.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListActivity : AppCompatActivity(),
        SwipeRefreshLayout.OnRefreshListener,
        AdapterView.OnItemSelectedListener,
        MovieItemClickListener {

    companion object {
        private const val TAG = "MovieListActivity"
    }

    // view and data binding
    private lateinit var mBinding: ActivityMovieListBinding

    // view model
    private val mViewModel : MovieListViewModel by viewModels()

    // adapter
    private lateinit var mAdapter : MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list)
        mBinding.let {
            it.viewModel = mViewModel
            it.lifecycleOwner = this
        }

        setupRecyclerView()

        mBinding.swipeRefresh.setOnRefreshListener(this)
        mBinding.spinner.onItemSelectedListener = this

    }

    override fun onRefresh() {
        mViewModel.moveDataSource.value?.invalidate()
        mBinding.swipeRefresh.isRefreshing = false
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        mViewModel.getMovieList("2016-12-31", mViewModel.spinnerDataList[position])
                .observe(this, {
                    mAdapter.submitList(it)
                })
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    private fun setupRecyclerView()
    {
        mAdapter = MovieListAdapter(this)
        mBinding.recyclerview.layoutManager = LinearLayoutManager(this)
        mBinding.recyclerview.adapter = mAdapter
    }

    /**
     * Move to movie details when click on movie listing items
     */
    override fun onMovieItemClickListener(movie: Movie) {

        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("movie_id", movie.id)
        startActivity(intent)
    }

}