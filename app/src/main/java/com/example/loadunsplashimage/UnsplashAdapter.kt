package com.example.loadunsplashimage

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.loadunsplashimage.blurhash_image.BlurHash
import com.example.loadunsplashimage.blurhash_image.blurPlaceHolder
import com.example.loadunsplashimage.databinding.ItemImageBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UnsplashAdapter(
    private val onLoadNextPage: () -> Unit
) : RecyclerView.Adapter<UnsplashAdapter.ViewHolder>() {

    private val items: MutableList<UnsplashImage> = mutableListOf()

    fun setItems(items: List<UnsplashImage>) {
        val oldSize = this.items.size
        this.items.clear()
        notifyItemRangeRemoved(0, oldSize)
        this.items.addAll(items)
        notifyItemRangeInserted(0, items.size)
        Log.d("xyz", "Set item: ${this.items.size}")
    }

    fun addItems(items: List<UnsplashImage>) {
        val oldSize = this.items.size
        this.items.addAll(items)
        notifyItemRangeInserted(oldSize, items.size)
        Log.d("xyz", "total item: ${this.items.size}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)
    override fun getItemCount() = items.size

    var blurHash: BlurHash? = null

    inner class ViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            binding.image.apply {
                (blurHash ?: BlurHash(context, lruSize = 20, punch = 1F).also { blurHash = it })
                Glide.with(this)
                    .load(items[position].urls.regular)
                    .blurPlaceHolder(items[position].blurHash, this, blurHash!!) { builder ->
                        builder.into(this)
                    }

                setOnClickListener {
                    val preView = ImageView(context)
                    MaterialAlertDialogBuilder(context).setView(preView).show()

                    Glide.with(this)
                        .load(items[position].urls.regular)
                        .blurPlaceHolder(items[position].blurHash, this, blurHash!!) { builder ->
                            builder.into(preView)
                        }
                }
            }

            /*Glide
                .with(binding.root)
                .load(items[position].urls.regular)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_no_image)
                .addListener(GlideListener())
                .into(binding.image)*/

            //Load next page
            if (position == (itemCount - PAGE_THRESHOLD)) onLoadNextPage()
        }
    }

    class GlideListener : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            Log.d("xyz", "Image load failed: $model")
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }
    }

    companion object {
        private const val PAGE_THRESHOLD = 5
    }
}