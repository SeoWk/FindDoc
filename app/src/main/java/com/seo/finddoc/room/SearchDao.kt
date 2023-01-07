package com.seo.finddoc.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSearchWord(word: String)

    @Query("DELETE FROM search_word WHERE word = :word")
    fun deleteSearchWord(word: String)

    @Query("DELETE FROM search_word")
    fun deleteAll()

    @Query("SELECT * FROM search_word ORDER BY date DESC")
    fun getAllSearchWord(): LiveData<List<SearchWord>>
}