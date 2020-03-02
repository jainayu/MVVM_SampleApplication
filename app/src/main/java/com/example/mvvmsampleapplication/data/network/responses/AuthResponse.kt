package com.example.mvvmsampleapplication.data.network.responses

import com.example.mvvmsampleapplication.data.db.entities.User

data class AuthResponse (
    val isSuccessful : Boolean?,
    val message: String?,
    val user: User?
){
}