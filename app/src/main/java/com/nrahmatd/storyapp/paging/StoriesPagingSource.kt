package com.nrahmatd.storyapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nrahmatd.storyapp.home.model.AllStoriesModel
import com.nrahmatd.storyapp.network.ApiRepository

class StoriesPagingSource(private val apiRepository: ApiRepository) : PagingSource<Int, AllStoriesModel.Story>() {

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, AllStoriesModel.Story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AllStoriesModel.Story> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiRepository.getAllStories()
//            val responseData = apiRepository.getAllStories(position, params.loadSize)

            LoadResult.Page(
                data = responseData.body()?.listStory ?: emptyList(),
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.body()?.listStory.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}
