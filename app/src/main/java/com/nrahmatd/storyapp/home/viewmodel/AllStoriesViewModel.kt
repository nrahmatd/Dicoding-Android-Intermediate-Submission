package com.nrahmatd.storyapp.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.nrahmatd.storyapp.app.GlobalApp
import com.nrahmatd.storyapp.database.local.StoriesDatabase
import com.nrahmatd.storyapp.home.model.AllStoriesModel
import com.nrahmatd.storyapp.network.ApiRepository
import com.nrahmatd.storyapp.network.exception.ApiException
import com.nrahmatd.storyapp.network.exception.NoInternetException
import com.nrahmatd.storyapp.paging.StoriesRemoteMediator
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

    var responsePaging: LiveData<PagingData<AllStoriesModel.Story>> = getAllStories().cachedIn(viewModelScope)

    private fun getAllStories(): LiveData<PagingData<AllStoriesModel.Story>> {
        val storiesDatabase = StoriesDatabase.getDatabase(GlobalApp.getAppContext())
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoriesRemoteMediator(storiesDatabase, repository),
            pagingSourceFactory = {
                storiesDatabase.storiesDao().getAllQuote()
//                StoriesPagingSource(repository)
            }
        ).liveData
    }
}
