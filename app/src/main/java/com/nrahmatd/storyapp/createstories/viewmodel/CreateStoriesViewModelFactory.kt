package com.nrahmatd.storyapp.createstories.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.nrahmatd.storyapp.di.Injection

class CreateStoriesViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        @Suppress("UNCHECKED_CAST")
        return CreateStoriesViewModel(Injection.provideRepository(context)) as T
    }
}
