package com.example.mvvmsampleapplication.ui.auth

import androidx.lifecycle.LiveData
import com.example.mvvmsampleapplication.data.db.entities.User

interface AuthListener  {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
}