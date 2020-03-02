package com.example.mvvmsampleapplication.data.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmsampleapplication.data.db.AppDatabase
import com.example.mvvmsampleapplication.data.db.entities.Quote
import com.example.mvvmsampleapplication.data.network.MyApi
import com.example.mvvmsampleapplication.data.network.SafeApiRequest
import com.example.mvvmsampleapplication.data.preferences.PreferenceProvider
import com.example.mvvmsampleapplication.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private val MINIMUM_INTERVAL = 6
class QuotesRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider

): SafeApiRequest(){
    private val quotes = MutableLiveData<List<Quote>>()

    init{
        quotes.observeForever{
            saveQuotes(it)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun  getQuotes(): LiveData<List<Quote>>{
        return withContext(Dispatchers.IO){
            fetchQuotes()
            db.getQuteDao().getQuotes()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun fetchQuotes(){
        val lastSavedAt = prefs.getLastSavedAt()
        if(lastSavedAt==null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))){
            val response = apiRequest{ api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return ChronoUnit.HOURS.between(savedAt, LocalDateTime.now())> MINIMUM_INTERVAL
    }

    private fun saveQuotes(quotes: List<Quote>) {
        Coroutines.io{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                prefs.saveLastSavedAt(LocalDateTime.now().toString())
            }
            db.getQuteDao().saveAllQuotes(quotes)
        }
    }
}