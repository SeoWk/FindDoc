package com.seo.finddoc.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    //이전 검색어로 조회
/*    @Query("SELECT * FROM search_word WHERE search_id LIKE :id")
    fun findSearchWord(id: Int): List<SearchWord>*/

    //전체조회
    @Query("SELECT * FROM search_word ORDER BY date DESC, search_id DESC")
    fun getAllSearchWord(): Flow<List<SearchWord>>

    //추가
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchWord(searchWord: SearchWord)

    //1개 삭제
    @Delete
    suspend fun deleteSearchWord(searchWord: SearchWord)
/*
@Query("DELETE FROM search_word WHERE word LIKE :word AND date LIKE :date LIMIT 1")
    suspend fun deleteSearchWord(word: String, date: String)
    */

    //모두 삭제
    @Query("DELETE FROM search_word")
    suspend fun deleteAll()

}