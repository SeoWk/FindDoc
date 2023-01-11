package com.seo.finddoc.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "search_word")
class SearchWord{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "search_id")
    var id: Int = 0

    var word: String ? = null

    var date: String  ? = null

    constructor() {}

    @Ignore
    constructor(id: Int, word: String, date: String) {
        this.id = id
        this.word = word
        this.date = date
    }

    @Ignore
    constructor(word: String, date: String) {
        this.word = word
        this.date = date
    }

    @Ignore
    constructor(word: String) {
        this.word = word
    }

    @Ignore
    constructor(id: Int) {
        this.id = id
    }
}



