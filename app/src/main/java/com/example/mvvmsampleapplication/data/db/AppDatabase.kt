package com.example.mvvmsampleapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvmsampleapplication.data.db.entities.Quote
import com.example.mvvmsampleapplication.data.db.entities.User

@Database(
    entities = [User::class, Quote::class],
    version =1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getUserDao(): UserDao
    abstract fun getQuteDao(): QuoteDao

    companion object{

        @Volatile
        private var instance: AppDatabase? = null
        private var LOCK = Any()

        operator  fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?:buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase (context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "MyDatabase.db"
            ).build()
    }
}