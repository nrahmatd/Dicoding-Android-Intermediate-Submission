package com.nrahmatd.storyapp.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.nrahmatd.storyapp.DataDummy
import com.nrahmatd.storyapp.MainDispatcherRule
import com.nrahmatd.storyapp.getOrAwaitValue
import com.nrahmatd.storyapp.home.adapter.HomeAdapter
import com.nrahmatd.storyapp.home.model.AllStoriesModel
import com.nrahmatd.storyapp.network.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AllStoriesViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Test
    fun `when Get Story Should Not Null and Return Success`() = runTest {
        val dummyStory = DataDummy.generateDummyStoriesResponse()
        val data: PagingData<AllStoriesModel.Story> = StoriesPagingSource.snapshot(dummyStory)
        val expectedStory = MutableLiveData<PagingData<AllStoriesModel.Story>>()
        expectedStory.value = data
        Mockito.`when`(apiRepository.getAllStoriesPaging()).thenReturn(expectedStory)

        val allStoriesViewModel = AllStoriesViewModel(apiRepository)
        val actualStories: PagingData<AllStoriesModel.Story> =
            allStoriesViewModel.responsePaging.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = HomeAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStories)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStory, differ.snapshot())
        Assert.assertEquals(dummyStory.size, differ.snapshot().size)
        Assert.assertEquals(dummyStory[0].name, differ.snapshot()[0]?.name)
    }
}

class StoriesPagingSource : PagingSource<Int, LiveData<List<AllStoriesModel.Story>>>() {
    companion object {
        fun snapshot(items: List<AllStoriesModel.Story>): PagingData<AllStoriesModel.Story> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<AllStoriesModel.Story>>>): Int? {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<AllStoriesModel.Story>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}
