package com.seo.finddoc.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SearchWord::class], exportSchema = false, version = 1)
abstract class SearchRoomDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao

    companion object {
        private lateinit var INSTANCE: SearchRoomDatabase
        internal fun getDatabase(context: Context): SearchRoomDatabase {
            if (!this::INSTANCE.isInitialized) {
                synchronized(SearchRoomDatabase::class.java) {
                    INSTANCE =
                        Room.databaseBuilder(
                            context.applicationContext,
                            SearchRoomDatabase::class.java,
                            "search_database"
                        ).build()
                }
            }
            return INSTANCE
        }
    }
}