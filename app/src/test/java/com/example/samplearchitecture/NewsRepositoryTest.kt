package com.example.samplearchitecture

import com.example.samplearchitecture.data.remote.NewsApiService
import com.example.samplearchitecture.data.repository.NewsRepository
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NewsRepositoryTest {
    private lateinit var mockwebserver: MockWebServer
    private lateinit var apiService: NewsApiService
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setup() {
        mockwebserver = MockWebServer()
        mockwebserver.start()

        val moshi = Moshi.Builder().build()
        val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }).build()

        apiService = Retrofit.Builder()
            .baseUrl(mockwebserver.url("/"))
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(NewsApiService::class.java)

        newsRepository = NewsRepository(apiService)
    }

    @Test
    fun getArticles_thenParseData_success() = runBlocking {
        // Implement test logic here
        val mockJson = """
            {
              "status": "ok",
              "totalResults": 1,
              "articles": [
                {
                  "title": "Breaking News!",
                  "description": "Something happened.",
                  "url": "https://news.com/article1",
                  "urlToImage": "https://news.com/image1.jpg",
                  "content": "Full article content here."
                }
              ]
            }
        """.trimIndent()

        mockwebserver.enqueue(MockResponse(200, body = mockJson))

        val article = apiService.getTopHeadlines().articles
        assert(article.size == 1)
        assert(article.first().title == "Breaking News!")
    }

    @After
    fun teardown() {
        mockwebserver.close()
    }
}