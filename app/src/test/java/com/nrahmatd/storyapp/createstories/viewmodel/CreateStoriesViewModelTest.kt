package com.nrahmatd.storyapp.createstories.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nrahmatd.storyapp.MainDispatcherRule
import com.nrahmatd.storyapp.getOrAwaitValue
import com.nrahmatd.storyapp.network.ApiRepository
import com.nrahmatd.storyapp.utils.ResponseHelper
import java.io.File
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CreateStoriesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var apiRepository: ApiRepository
    private lateinit var createStoriesViewModel: CreateStoriesViewModel

    @Before
    fun setUp() {
        createStoriesViewModel = CreateStoriesViewModel(apiRepository)
    }

    @Test
    fun `when Create Story and Result Success`() = runTest {
        val observer = Observer<ResponseHelper> {}
        try {
            val photoFile = File("")
            val requestImageFile = photoFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                "Pemandangan Alam",
                requestImageFile
            )
            val description =
                "Pemandangan".toRequestBody("text/plain".toMediaType())
            val latitude =
                "-6.268288".toRequestBody("text/plain".toMediaType())
            val longitude =
                "106.89265".toRequestBody("text/plain".toMediaType())

            createStoriesViewModel.createStories(
                101,
                file = imageMultipart,
                description = description,
                lat = latitude,
                long = longitude
            )

            val actualCreateStories = createStoriesViewModel.response.getOrAwaitValue()

            Mockito.verify(apiRepository).createStories(
                file = imageMultipart,
                description = description,
                lat = latitude,
                long = longitude
            )
            Assert.assertNotNull(actualCreateStories)
        } finally {
            createStoriesViewModel.response.removeObserver(observer)
        }
    }
}
