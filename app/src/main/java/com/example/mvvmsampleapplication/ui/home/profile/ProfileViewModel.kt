package com.example.mvvmsampleapplication.ui.home.profile

import androidx.lifecycle.ViewModel
import com.example.mvvmsampleapplication.data.repositories.UserRepository

class ProfileViewModel(
    repository: UserRepository
) : ViewModel() {

    val user = repository.getUser()

}
