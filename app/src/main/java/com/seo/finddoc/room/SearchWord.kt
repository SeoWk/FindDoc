package com.seo.finddoc.room

import androidx.room.Entity

@Entity(tableName = "search_word")
data class SearchWord(
/*    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "search_id")
    var id: Int = 0,*/

    val word: String,
    val date: String
)
