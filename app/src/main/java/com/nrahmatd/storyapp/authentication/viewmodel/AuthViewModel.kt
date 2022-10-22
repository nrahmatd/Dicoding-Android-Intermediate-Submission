package com.nrahmatd.storyapp.authentication.viewmodel

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

class AuthViewModel(private val repository: ApiRepository) : ViewModel() {

    var response = MutableLiveData<ResponseHelper>()

    fun login(code_request: Int, email: String, password: String) = viewModelScope.launch {
        setResponse(response, ResponseHelper.LOADING, true)
        try {
            val request = repository.login(email, password)
            setRequest(response, request, code_request)
            setResponse(response, ResponseHelper.LOADING, false)
        } catch (e: Exception) {
            if (e is ApiException || e is NoInternetException) {
                setResponse(response, ResponseHelper.ERROR, e.message.toString())
                setResponse(response, ResponseHelper.LOADING, false)
            }
        }
    }

    fun register(code_request: Int, name: String, email: String, password: String) = viewModelScope.launch {
        setResponse(response, ResponseHelper.LOADING, true)
        try {
            val request = repository.register(name, email, password)
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
