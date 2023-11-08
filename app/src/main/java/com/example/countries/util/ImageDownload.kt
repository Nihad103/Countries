package com.example.countries.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide


fun ImageView.downloadImageFromUrl(url: String?, progressDrawable: CircularProgressDrawable) {
    Glide.with(context)
        .load(url)
        .placeholder(progressDrawable(context))
        .into(this)
}

fun progressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("downloadImage")
fun imageDownload(view: ImageView, url: String?) {
    view.downloadImageFromUrl(url, progressDrawable(view.context))
}