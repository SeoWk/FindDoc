package com.seo.finddoc.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_word")
data class SearchWord(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "search_id")
    var id: Int = 0,

    val word: String,
    val date: String
)
