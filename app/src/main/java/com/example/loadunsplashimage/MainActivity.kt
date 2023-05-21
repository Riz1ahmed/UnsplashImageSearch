package com.example.loadunsplashimage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.loadunsplashimage.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var unsplashAdapter: UnsplashAdapter

    lateinit var unsplashApi: UnsplashApiService

    private var currentPage = 1
    private val lastQuery = "nature"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRetrofit()

        unsplashAdapter = UnsplashAdapter(onLoadNextPage = {
            showToast("Loading next page...")
            searchInUnSplash(
                query = null,
                contentFilter = getContentFilter()
            ) { unsplashImages ->
                unsplashAdapter.addItems(unsplashImages)
            }
        })
        binding.recyclerView.adapter = unsplashAdapter
        setupSearch()
    }

    private fun setupSearch() {
        binding.btnSearch.setOnClickListener {
            binding.textInput.text?.toString()?.let { text ->

                searchInUnSplash(
                    query = text,
                    contentFilter = getContentFilter()
                ) { unsplashImages ->
                    unsplashAdapter.setItems(unsplashImages)
                }
            } ?: showToast("Please input Text")
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->

        }
    }

    private fun getContentFilter() =
        if (binding.radioGroup.checkedRadioButtonId == binding.radioLow.id) "low" else "high"

    private fun initRetrofit() {
        val retrofit = AppHelper.getRetrofit()
        unsplashApi = retrofit.create(UnsplashApiService::class.java)
    }

    private fun searchInUnSplash(
        query: String?,
        contentFilter: String,
        onImages: (images: List<UnsplashImage>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {

            val queryText = if (query == null) {
                currentPage++
                lastQuery
            } else query

            unsplashApi.getImages(
                query = queryText,
                page = currentPage,
                contentFilter = contentFilter
            ).let { response ->
                if (response.isSuccessful && response.body() != null) {
                    val images: List<UnsplashImage> = response.body()!!.result
                    withContext(Main) { onImages(images) }

                    Log.d("xyz", "queryText: $query")
                    images.forEach { Log.d("xyz", "$it") }
                } else {
                    withContext(Main) {
                        showToast("Something went wrong.")
                    }
                }
            }
        }
    }

    companion object {
        const val IMAGE_PER_PAGE = 20
    }
}