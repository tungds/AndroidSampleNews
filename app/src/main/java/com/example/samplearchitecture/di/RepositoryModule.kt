package com.example.samplearchitecture.di

import com.example.samplearchitecture.data.remote.NewsApiService
import com.example.samplearchitecture.data.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    // Add repository-related providers here in the future
    @Provides
    @Singleton
    fun provideRepository(apiService: NewsApiService): NewsRepository {
        // Implementation for providing repositories
        return NewsRepository(apiService)
    }
}