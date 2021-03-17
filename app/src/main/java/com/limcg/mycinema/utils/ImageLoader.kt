package com.limcg.mycinema.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageLoader {

    /**
     * Data binding with xml layout view
     */
    @JvmStatic
    @BindingAdapter("bindMovieImage")
    fun loadImage(view: ImageView, image_url: String?) {
        if (image_url != null)
        {
            val imageBasePath = "https://image.tmdb.org/t/p/original"
            val imagePath = imageBasePath + image_url
            Glide.with(view.context)
                    .load(imagePath)
                    .into(view)
        }

    }
}