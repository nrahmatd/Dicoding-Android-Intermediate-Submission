package com.nrahmatd.storyapp.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.nrahmatd.storyapp.database.local.RemoteKeys
import com.nrahmatd.storyapp.database.local.StoriesDatabase
import com.nrahmatd.storyapp.database.sharedpref.PreferenceManager
import com.nrahmatd.storyapp.home.model.AllStoriesModel
import com.nrahmatd.storyapp.network.ApiServices
import com.nrahmatd.storyapp.utils.GlobalVariable

@OptIn(ExperimentalPagingApi::class)
class StoriesRemoteMediator(
    private val database: StoriesDatabase,
    private val apiServices: ApiServices
) : RemoteMediator<Int, AllStoriesModel.Story>() {

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AllStoriesModel.Story>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestCurrentPosition(state)
                remoteKeys?.nextKeys?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKeys ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKeys = remoteKeys?.nextKeys ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                nextKeys
            }
        }

        try {
            val accessToken =
                "Bearer ".plus(PreferenceManager.instance.getString(GlobalVariable.ACCESS_TOKEN, ""))
            val responseData = apiServices.getAllStories(
                token = accessToken,
                page = page,
                size = state.config.pageSize,
                location = 1
            )

            val endPaginationReached = responseData.body()?.listStory?.isEmpty() ?: false

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().deleteRemoteKeys()
                    database.storiesDao().deleteAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endPaginationReached) null else page + 1
                val keys = responseData.body()?.listStory?.map {
                    RemoteKeys(id = it.id, prevKeys = prevKey, nextKeys = nextKey)
                }

                database.remoteKeysDao().insertAll(keys)
                database.storiesDao().insertStories(responseData.body()?.listStory)
            }

            return MediatorResult.Success(endOfPaginationReached = endPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, AllStoriesModel.Story>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysById(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, AllStoriesModel.Story>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysById(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestCurrentPosition(state: PagingState<Int, AllStoriesModel.Story>): RemoteKeys? {
        return state.anchorPosition.let { position ->
            state.closestItemToPosition(position ?: 0)?.id?.let { id ->
                database.remoteKeysDao().getRemoteKeysById(id)
            }
        }
    }
}
