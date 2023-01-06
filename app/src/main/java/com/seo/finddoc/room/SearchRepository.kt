package com.seo.finddoc.room

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class SearchRepository(application: Application) {

    val searchResults = MutableLiveData<List<SearchWord>>()
    private lateinit var SearchDao: SearchDao
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    var allProducts: LiveData<List<SearchWord>>? = null


}