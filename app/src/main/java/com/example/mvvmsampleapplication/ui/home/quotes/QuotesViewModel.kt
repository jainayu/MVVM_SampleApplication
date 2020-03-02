package com.example.mvvmsampleapplication.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.example.mvvmsampleapplication.data.repositories.QuotesRepository
import com.example.mvvmsampleapplication.util.lazyDeferred

class QuotesViewModel(
    repository: QuotesRepository
) : ViewModel() {

    val quotes by lazyDeferred {
        repository.getQuotes()
    }
}
