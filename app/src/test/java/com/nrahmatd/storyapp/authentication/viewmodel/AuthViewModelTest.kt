package com.nrahmatd.storyapp.authentication.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nrahmatd.storyapp.MainDispatcherRule
import com.nrahmatd.storyapp.getOrAwaitValue
import com.nrahmatd.storyapp.network.ApiRepository
import com.nrahmatd.storyapp.utils.ResponseHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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
class AuthViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var apiRepository: ApiRepository
    private lateinit var authViewModel: AuthViewModel

    private val name = "User 101"
    private val email = "user@gmail.com"
    private val password = "12345678"

    @Before
    fun setUp() {
        authViewModel = AuthViewModel(apiRepository)
    }

    @Test
    fun `when Register and Result Success`() = runTest {
        val observer = Observer<ResponseHelper> {}
        try {

            authViewModel.register(
                101,
                name = name,
                email = email,
                password = password
            )

            val actualRegister = authViewModel.response.getOrAwaitValue()

            Mockito.verify(apiRepository).register(
                name = name,
                email = email,
                password = password
            )
            Assert.assertNotNull(actualRegister)
        } finally {
            authViewModel.response.removeObserver(observer)
        }
    }

    @Test
    fun `when Login and Result Success`() = runTest {
        val observer = Observer<ResponseHelper> {}
        try {

            authViewModel.login(
                101,
                email = email,
                password = password
            )

            val actualLogin = authViewModel.response.getOrAwaitValue()

            Mockito.verify(apiRepository).login(
                email = email,
                password = password
            )
            Assert.assertNotNull(actualLogin)
        } finally {
            authViewModel.response.removeObserver(observer)
        }
    }
}
