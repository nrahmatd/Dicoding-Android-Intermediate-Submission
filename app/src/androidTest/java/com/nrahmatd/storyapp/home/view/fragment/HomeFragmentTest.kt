package com.nrahmatd.storyapp.home.view.fragment

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.nrahmatd.storyapp.R
import com.nrahmatd.storyapp.home.view.MainActivity
import com.nrahmatd.storyapp.network.ApiConfig
import com.nrahmatd.storyapp.utils.EspressoIdlingResource
import com.nrahmatd.storyapp.utils.readStringFromFile
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

@MediumTest
class HomeFragmentTest {

    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.start(8080)
        ApiConfig.API_BASE_URL_MOCK = "http://127.0.0.1:8080/"
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun launchHomeFragment_Success() {

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(readStringFromFile("success_response.json"))
        mockWebServer.enqueue(mockResponse)

        Espresso.onView(withId(R.id.rv_home))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("Ahmad"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
