package com.seo.finddoc.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seo.finddoc.data.SearchedData
import java.text.SimpleDateFormat
import java.util.*

class SearchViewModel : ViewModel(){
    private val data = arrayListOf<SearchedData>()
    val liveData = MutableLiveData<List<SearchedData>>()

    fun addData(getKeyword: String){
        val stringDate = SimpleDateFormat("MM/dd", Locale.getDefault()).format(System.currentTimeMillis())

        data.add(SearchedData(
            getKeyword ,
            stringDate
        ))
        liveData.value = data
    }

    fun removeData(item: SearchedData){
        data.remove(item)
        liveData.value = data
    }
}