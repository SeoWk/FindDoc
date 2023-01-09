package com.seo.finddoc.room

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class SearchRepository(private val searchDao: SearchDao) {
    val allSearchWords: Flow<List<SearchWord>> = searchDao.getAllSearchWord()

    @WorkerThread
    suspend fun insertSearchWord(searchWord: SearchWord) {
        searchDao.insertSearchWord(searchWord)
    }
    @WorkerThread
    suspend fun deleteSearchWord(searchWord: SearchWord) {
        searchDao.deleteSearchWord(searchWord)
    }
    @WorkerThread
    suspend fun deleteAll() {
        searchDao.deleteAll()
    }

}

