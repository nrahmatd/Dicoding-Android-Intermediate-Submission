package com.nrahmatd.storyapp.createstories.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nrahmatd.storyapp.network.ApiRepository
import com.nrahmatd.storyapp.network.exception.ApiException
import com.nrahmatd.storyapp.network.exception.NoInternetException
import com.nrahmatd.storyapp.utils.ResponseHelper
import com.nrahmatd.storyapp.utils.setRequest
import com.nrahmatd.storyapp.utils.setResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CreateStoriesViewModel(private val repository: ApiRepository) : ViewModel() {

    var response = MutableLiveData<ResponseHelper>()

    fun createStories(
        code_request: Int,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        long: RequestBody?
    ) = viewModelScope.launch {
        setResponse(response, ResponseHelper.LOADING, true)
        try {
            val request = repository.createStories(file, description, lat, long)
            setRequest(response, request, code_request)
            setResponse(response, ResponseHelper.LOADING, false)
        } catch (e: Exception) {
            if (e is ApiException || e is NoInternetException) {
                setResponse(response, ResponseHelper.ERROR, e.message.toString())
                setResponse(response, ResponseHelper.LOADING, false)
            }
        }
    }
}
