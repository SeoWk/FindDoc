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
        internal fun getDatabase(
            context: Context,
//            scope: CoroutineScope
        ): SearchRoomDatabase {
            if (!this::INSTANCE.isInitialized) {
                synchronized(SearchRoomDatabase::class.java) {
                    INSTANCE =
                        Room.databaseBuilder(
                            context.applicationContext,
                            SearchRoomDatabase::class.java,
                            "search_database"
                        )
 /*                           .addCallback(object : Callback () {
                                override fun onCreate (db: SupportSQLiteDatabase) {
                                    super. onCreate(db)
                                }
                            })*/
                            .build()
                }
            }
            return INSTANCE
        }
    }

    /**
     * 삭제예정 -  CoroutineScope를 매개변수로 갖는 Callback
     */
/*    private class SearchDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var searchDao = database.searchDao()

                    // deleteAll
                    searchDao.deleteAll()

                    // insert
                }
            }
        }
    }*/
}