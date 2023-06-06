package com.example.hotnews

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.hotnews.data.NewsService
import com.example.hotnews.data.network.response.Articles
import com.example.hotnews.data.network.response.News
import com.example.hotnews.presentation.home.HomeViewModel
import com.example.hotnews.presentation.repository.NewsRepository
import com.example.hotnews.presentation.repository.NewsRepositoryImpl
import com.example.hotnews.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.any
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.given
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import retrofit2.Response
import java.lang.RuntimeException
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner.Silent::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val application = mock(Application::class.java)
    private val newsService = mock(NewsService::class.java)

    private val news = News(
        listOf(Articles(
            title = "Title",
            author = "Author",
            content = "Content",
            url = "url",
            urlToImage = null
        ))
    )

    @Test
    fun testGettingSuccessfullyTopHeadlinesNews() = runTest {
        val newsRepository: NewsRepository = NewsRepositoryImpl(
            dispatchers = UnconfinedTestDispatcher(testScheduler),
            newsService = newsService)

        `when`(application.getString(R.string.no_network)).thenReturn("Please check your internet connection…")
        `when`(application.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(mock(ConnectivityManager::class.java))
        `when`(newsService.getTopHeadlines(anyString(), anyString())).thenReturn(Response.success(news))
        `when`(newsRepository.getTopHeadlines(anyString(), anyString())).thenReturn(Result.Success(news))

        val viewModel = HomeViewModel(repository = newsRepository, application = application)

        Assert.assertEquals(0, viewModel.articles.value.size)
        Assert.assertTrue(viewModel.articles.value.isEmpty())

        viewModel.articles.value = news.articles

        Assert.assertNotEquals(0, viewModel.articles.value.size)
        Assert.assertEquals("Content", viewModel.articles.value[0].content)
        Assert.assertFalse(viewModel.articles.value.isEmpty())
    }

    @Test
    fun testHandlingAnExceptionInTopHeadlinesNews() = runTest {
        val newsRepository: NewsRepository = NewsRepositoryImpl(
            dispatchers = UnconfinedTestDispatcher(testScheduler),
            newsService = newsService)

        `when`(application.getString(R.string.no_network)).thenReturn("Please check your internet connection…")
        `when`(application.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(mock(ConnectivityManager::class.java))
        `when`(newsService.getTopHeadlines(anyString(), anyString())).thenReturn(Response.success(news))
        given(newsRepository.getTopHeadlines(anyString(), anyString())).willAnswer { throw UnknownHostException() }

        val viewModel = HomeViewModel(repository = newsRepository, application = application)

        verify(application, times(1)).getString(R.string.no_network)

        Assert.assertEquals(0, viewModel.articles.value.size)
        Assert.assertTrue(viewModel.articles.value.isEmpty())
    }
}