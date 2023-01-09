package com.seo.finddoc.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    //추가
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchWord(searchWord: SearchWord)

    //1개 삭제
    /**
     * 검색어와 날짜가 같은 항목 삭제
     */
    @Delete
    suspend fun deleteSearchWord(searchWord: SearchWord)
//    @Query("DELETE FROM search_word WHERE word LIKE :word AND date LIKE :date LIMIT 1")
//    suspend fun deleteSearchWord(word: String, date: String)

    //모두 삭제
    @Query("DELETE FROM search_word")
    suspend fun deleteAll()

    //조회
    @Query("SELECT * FROM search_word ORDER BY date DESC")
    fun getAllSearchWord(): Flow<List<SearchWord>>
}