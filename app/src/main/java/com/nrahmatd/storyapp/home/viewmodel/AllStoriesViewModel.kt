package com.nrahmatd.storyapp.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nrahmatd.storyapp.home.model.AllStoriesModel
import com.nrahmatd.storyapp.network.ApiRepository
import com.nrahmatd.storyapp.network.exception.ApiException
import com.nrahmatd.storyapp.network.exception.NoInternetException
import com.nrahmatd.storyapp.utils.ResponseHelper
import com.nrahmatd.storyapp.utils.setRequest
import com.nrahmatd.storyapp.utils.setResponse
import kotlinx.coroutines.launch

class AllStoriesViewModel(private val repository: ApiRepository) : ViewModel() {

    var response = MutableLiveData<ResponseHelper>()

    fun getAllStories(code_request: Int) = viewModelScope.launch {
        setResponse(response, ResponseHelper.LOADING, true)
        try {
            val request = repository.getAllStories()
            setRequest(response, request, code_request)
            setResponse(response, ResponseHelper.LOADING, false)
        } catch (e: Exception) {
            if (e is ApiException || e is NoInternetException) {
                setResponse(response, ResponseHelper.ERROR, e.message.toString())
                setResponse(response, ResponseHelper.LOADING, false)
            }
        }
    }

    var responsePaging: LiveData<PagingData<AllStoriesModel.Story>> = repository.getAllStoriesPaging().cachedIn(viewModelScope)
}
