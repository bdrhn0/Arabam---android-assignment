package com.bedirhandag.arabamcomandroidsample.util

import android.widget.ImageView
import com.bedirhandag.arabamcomandroidsample.R
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String, ) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.ic_no_photo)
        .into(this)
}
