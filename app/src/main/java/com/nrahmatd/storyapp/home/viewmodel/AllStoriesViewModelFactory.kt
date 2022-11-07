package com.nrahmatd.storyapp.home.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.nrahmatd.storyapp.di.Injection

class AllStoriesViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        @Suppress("UNCHECKED_CAST")
        return AllStoriesViewModel(Injection.provideRepository(context)) as T
    }

    /** In lifecycle 2.4.+ doesn't support */
    /*
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AuthViewModel(ApiRepository()) as T
    }
     */
}
