package com.nrahmatd.storyapp.network

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.nrahmatd.storyapp.database.local.StoriesDatabase
import com.nrahmatd.storyapp.database.sharedpref.PreferenceManager
import com.nrahmatd.storyapp.home.model.AllStoriesModel
import com.nrahmatd.storyapp.paging.StoriesRemoteMediator
import com.nrahmatd.storyapp.utils.GlobalVariable
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ApiRepository(private val database: StoriesDatabase, private val apiServices: ApiServices) {

    private val accessToken =
        "Bearer ".plus(PreferenceManager.instance.getString(GlobalVariable.ACCESS_TOKEN, ""))

    /** Authentication **/
    suspend fun login(email: String, password: String) = apiServices.login(email, password)
    suspend fun register(name: String, email: String, password: String) =
        apiServices.register(name, email, password)

    /** Stories **/
    suspend fun getAllStories() =
        apiServices.getAllStories(accessToken, null, null, 1)

    suspend fun getAllStoriesPaging(page: Int, size: Int) =
        apiServices.getAllStories(accessToken, page, size, 1)

    fun getAllStoriesPaging(): LiveData<PagingData<AllStoriesModel.Story>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoriesRemoteMediator(database, apiServices),
            pagingSourceFactory = {
                database.storiesDao().getAllQuote()
//                StoriesPagingSource(repository)
            }
        ).liveData
    }

    suspend fun createStories(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        long: RequestBody?
    ) =
        apiServices.createStories(accessToken, file, description, lat, long)
}
