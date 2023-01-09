package com.seo.finddoc.room

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository) : ViewModel(){

    val allWords: LiveData<List<SearchWord>> = repository.allSearchWords.asLiveData()

    fun insertSearchWord(searchWord: SearchWord) = viewModelScope.launch {
        repository.insertSearchWord(searchWord)
    }
    fun deleteSearchWord(searchWord: SearchWord) = viewModelScope.launch {
        repository.deleteSearchWord(searchWord)
    }
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

/*
    private val data = arrayListOf<SearchWord>()
    val liveData = MutableLiveData<List<SearchWord>>()

fun addData(word: String){
        val stringDate = SimpleDateFormat("MM/dd", Locale.getDefault()).format(System.currentTimeMillis())

        data.add(
            SearchWord(
                word ,
                stringDate
            )
        )
        liveData.value = data
    }

    fun removeData(item: SearchWord){
        data.remove(item)
        liveData.value = data
    }
 */
}

class SearchViewModelFactory(private val repository: SearchRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

