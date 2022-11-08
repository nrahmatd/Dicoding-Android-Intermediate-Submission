package com.nrahmatd.storyapp.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nrahmatd.storyapp.authentication.model.LoginModel
import com.nrahmatd.storyapp.database.local.StoriesDatabase
import com.nrahmatd.storyapp.home.model.AllStoriesModel
import com.nrahmatd.storyapp.network.ApiServices
import com.nrahmatd.storyapp.utils.general_model.GeneralModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class StoriesRemoteMediatorTest {

    private var mockApi: ApiServices = FakeApiServices()
    private var mockDB: StoriesDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(), StoriesDatabase::class.java
    ).allowMainThreadQueries().build()

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runBlocking {
        val remoteMediator = StoriesRemoteMediator(
            mockDB, mockApi
        )

        val pagingState = PagingState<Int, AllStoriesModel.Story>(
            listOf(), null, PagingConfig(10), 10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @After
    fun tearDown() {
        mockDB.clearAllTables()
    }
}

class FakeApiServices : ApiServices {
    override suspend fun login(email: String, password: String): Response<LoginModel> {
        val loginResult = LoginModel.LoginResult(
            name = "ahmad", token = "1001", userId = "0110"
        )
        val loginModel = LoginModel(
            error = false, message = "Success", loginResult = loginResult
        )
        return Response.success(loginModel)
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Response<GeneralModel> {
        val generalModel = GeneralModel(
            error = false, message = "Success"
        )
        return Response.success(generalModel)
    }

    override suspend fun getAllStories(
        token: String,
        page: Int?,
        size: Int?,
        location: Int?
    ): Response<AllStoriesModel> {
        val items: MutableList<AllStoriesModel.Story> = arrayListOf()
        for (i in 0..100) {
            val story = AllStoriesModel.Story(
                id = i.toString(),
                name = "Mamat",
                description = "Alat Pemadam Kebakaran",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1667831122851_lKTktHqW.jpg",
                createdAt = "2022-11-07T14:25:22.854Z",
                lat = -6.268288,
                lon = 106.89265
            )
            items.add(story)
        }

        val allStoriesModel = AllStoriesModel(
            error = false, message = "Success", listStory = items
        )
        return Response.success(allStoriesModel)
    }

    override suspend fun createStories(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ): Response<GeneralModel> {
        val generalModel = GeneralModel(
            error = false, message = "Success"
        )
        return Response.success(generalModel)
    }
}
