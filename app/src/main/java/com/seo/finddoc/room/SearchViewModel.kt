package com.seo.finddoc.room

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class SearchViewModel : ViewModel(){
    private val data = arrayListOf<SearchWord>()
    val liveData = MutableLiveData<List<SearchWord>>()

    fun addData(getKeyword: String){
        val stringDate = SimpleDateFormat("MM/dd", Locale.getDefault()).format(System.currentTimeMillis())

        data.add(
            SearchWord(
            getKeyword ,
            stringDate
        )
        )
        liveData.value = data
    }

    fun removeData(item: SearchWord){
        data.remove(item)
        liveData.value = data
    }
}