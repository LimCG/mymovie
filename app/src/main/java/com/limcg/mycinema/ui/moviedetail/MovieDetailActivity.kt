package com.limcg.mycinema.ui.moviedetail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.limcg.mycinema.R
import com.limcg.mycinema.databinding.ActivityMovieDetailBinding
import com.limcg.mycinema.repository.entities.Genres
import com.limcg.mycinema.repository.entities.Movie
import com.limcg.mycinema.repository.entities.MovieLanguage
import com.limcg.mycinema.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity(), MovieDetailClickListener {

    companion object {
        @JvmStatic
        @BindingAdapter("setGenres")
        fun setGenres(view: TextView, genresList: MutableList<Genres>?) {
            if (genresList != null)
            {
                val stringBuilder = StringBuilder()
                for (item in genresList.indices)
                {
                    stringBuilder.append(genresList[item].name)
                    if(item != genresList.size -1)
                    {
                        stringBuilder.append(", ")
                    }

                }

                view.text = stringBuilder.toString()
            }
        }

        @JvmStatic
        @BindingAdapter("setLanguages")
        fun setLanguages(view: TextView, languages: MutableList<MovieLanguage>?) {
            if (languages != null)
            {
                val stringBuilder = StringBuilder()
                for (item in languages.indices)
                {
                    stringBuilder.append(languages[item].name)
                    if(item != languages.size -1)
                    {
                        stringBuilder.append(", ")
                    }

                }

                view.text = stringBuilder.toString()
            }
        }
    }

    // binding
    private lateinit var mBinding : ActivityMovieDetailBinding

    // view model
    private val mViewModel : MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        mBinding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        mBinding.buttonClick = this

        if (intent.hasExtra("movie_id"))
        {
            val movie_id = intent.getStringExtra("movie_id") as String
            mViewModel.getMovieDetail(movie_id).observe(this, {

                when (it) {
                    is Resource.Loading -> {

                        // loading state
                    }

                    is Resource.Success -> {

                        // Data binding
                        mBinding.movieData = it.data
                    }

                    is Resource.Error -> {

                        // hide booking button if data information return empty
                        mBinding.buttonBook.visibility = View.GONE

                        // show error msg
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                    }

                }

            })

        }
        else
        {
            Toast.makeText(this, "Movie id not found", Toast.LENGTH_LONG).show()
        }
    }

    override fun onClickBookTheMovie(movie: Movie) {
        val booking_url = "https://www.cathaycineplexes.com.sg/"

        val customIntent = CustomTabsIntent.Builder()
        customIntent.setToolbarColor(ContextCompat.getColor(this, R.color.purple_200))
        openCustomTab(this, customIntent.build(), Uri.parse(booking_url))
    }

    fun openCustomTab(activity: Activity, customTabsIntent: CustomTabsIntent, uri: Uri) {
        // package name is the default package
        // for our custom chrome tab
        val packageName = "com.android.chrome"
        if (packageName != null) {

            // we are checking if the package name is not null
            // if package name is not null then we are calling
            // that custom chrome tab with intent by passing its
            // package name.
            customTabsIntent.intent.setPackage(packageName)

            // in that custom tab intent we are passing
            // our url which we have to browse.
            customTabsIntent.launchUrl(activity, uri)
        } else {
            // if the custom tabs fails to load then we are simply
            // redirecting our user to users device default browser.
            activity.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }
}