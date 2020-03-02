package com.example.mvvmsampleapplication.data.network.responses

import com.example.mvvmsampleapplication.data.db.entities.Quote

data class QuotesResponse(
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)
