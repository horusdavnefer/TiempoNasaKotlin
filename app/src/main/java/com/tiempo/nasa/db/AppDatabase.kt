package com.tiempo.nasa.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.tiempo.nasa.db.dao.ApolloDao
import com.tiempo.nasa.db.entities.Apollo

@Database(entities = [Apollo::class], version = 10)
abstract class AppDatabase : RoomDatabase() {
    abstract val apolloDao: ApolloDao?

    companion object {
        private val LOG_TAG = AppDatabase::class.java.simpleName
        private val LOCK = Any()
        private const val DATABASE_NAME = "NasaDatabase"
        private var mInstance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (mInstance == null) {
                synchronized(LOCK) {
                    mInstance = databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return mInstance
        }
    }
}