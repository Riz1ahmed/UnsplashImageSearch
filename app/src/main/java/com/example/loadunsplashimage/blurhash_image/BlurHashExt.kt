package com.example.loadunsplashimage.blurhash_image

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions

// FOR GLIDE
fun RequestBuilder<Drawable>.blurPlaceHolder(
    blurString: String,
    width: Int,
    height: Int,
    blurHash: BlurHash,
    response: (requestBuilder: RequestBuilder<Drawable>) -> Unit
) {
    if (width != 0 && height != 0) {
        blurHash.execute(blurString, width, height) { drawable ->
            placeholder(drawable)
            response(this)
        }
    }
}

fun RequestBuilder<Drawable>.blurPlaceHolder(
    blurString: String,
    targetView: View,
    blurHash: BlurHash,
    response: (requestBuilder: RequestBuilder<Drawable>) -> Unit
) {
    targetView.post {
        blurPlaceHolder(blurString, targetView.width, targetView.height, blurHash, response)
    }
}

fun RequestOptions.blurPlaceHolderOf(
    blurString: String,
    width: Int,
    height: Int,
    blurHash: BlurHash,
    response: (requestOptions: RequestOptions) -> Unit
) {
    if (width != 0 && height != 0) {
        blurHash.execute(blurString, width, height) { drawable ->
            placeholder(drawable)
            response(this)
        }
    }
}

fun RequestOptions.blurPlaceHolderOf(
    blurString: String,
    targetView: View,
    blurHash: BlurHash,
    response: (requestOptions: RequestOptions) -> Unit
) {
    targetView.post {
        blurPlaceHolderOf(blurString, targetView.width, targetView.height, blurHash, response)
    }
}

// FOR IMAGEVIEW
fun ImageView.blurPlaceHolder(
    blurString: String,
    blurHash: BlurHash,
    response: (drawable: Drawable) -> Unit
) {
    this.post {
        if (width != 0 && height != 0) {
            blurHash.execute(blurString, width, height) { drawable ->
                if (getDrawable() != null)
                    setImageDrawable(drawable)
                response(drawable)
            }
        }
    }
}

// GENERIC USAGE
fun blurHashDrawable(
    blurString: String,
    targetView: View,
    blurHash: BlurHash,
    response: (drawable: Drawable) -> Unit
) {
    targetView.post {
        blurHashDrawable(blurString, targetView.width, targetView.height, blurHash, response)
    }
}

fun blurHashDrawable(
    blurString: String,
    width: Int,
    height: Int,
    blurHash: BlurHash,
    response: (drawable: Drawable) -> Unit
) {
    if (width != 0 && height != 0) {
        blurHash.execute(blurString, width, height) { drawable ->
            response(drawable)
        }
    }
}