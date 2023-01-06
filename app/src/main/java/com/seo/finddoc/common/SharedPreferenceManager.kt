/*
package com.seo.finddoc.common

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.seo.finddoc.common.FindDocApplication.Companion.getAppInstance
import com.seo.finddoc.data.SearchedData

object SharedPreferenceManager {
    //프리퍼런스 키 값
    private const val SHARED_SEARCH_HISTORY = "shared_search_history"
    private const val KEY_SEARCH_HISTORY = "key_search_history"

    private lateinit var storedSearchHistoryList: ArrayList<SearchedData>
    private val sp: SharedPreferences =
        getAppInstance().getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)
    private val spEditor : SharedPreferences.Editor = sp.edit()

    //검색 목록 가져오기
    fun getSearchHistoryList(): ArrayList<SearchedData> {
        Log.d(TAG, "SharedPreferenceManager - getSearchHistoryList() called")
        val storedSearchHistoryListString: String = sp.getString(KEY_SEARCH_HISTORY, "")!!

        //검색 목록의 값이 있는 경우
        if (storedSearchHistoryListString.isNotEmpty()) {
            //저장된 문자열을 객체 배열로 변경
            storedSearchHistoryList =
                Gson()
                    .fromJson(storedSearchHistoryListString, Array<SearchedData>::class.java)
                    .toMutableList() as ArrayList<SearchedData>
        }
        return storedSearchHistoryList
    }

    // 검색 목록을 저장
    fun storeSearchHistoryList(searchHistoryList: ArrayList<SearchedData>) {
        Log.d(TAG, "SharedPreferenceManager - storeSearchHistoryList() called")

        //매개변수로 들어온 배열을 문자열로 저장
        val searchHistoryListString : String = Gson().toJson(searchHistoryList)
        Log.d(TAG, "SharedPreferenceManager - searchHistoryListString : $searchHistoryListString")

        spEditor.run {
            putString(KEY_SEARCH_HISTORY, searchHistoryListString)
            apply()
        }
    }

*/
/*    // 검색 목록 지우기
    fun clearSearchHistoryList(){
        Log.d(TAG, "SharedPreferenceManager - clearSearchHistoryList() called")
        val shared = getAppInstance().getSharedPreferences(SHARED_SHEARCH_HISTORY, Context.MODE_PRIVATE)
        val editor = shared.edit()

        editor.clear()

        editor.apply()
    }*//*

}*/
