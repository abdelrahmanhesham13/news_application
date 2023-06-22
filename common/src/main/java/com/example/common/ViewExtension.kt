package com.example.common

import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation

fun ImageView.loadImage(url: String) {
    load(url) {
        crossfade(true)
    }
}