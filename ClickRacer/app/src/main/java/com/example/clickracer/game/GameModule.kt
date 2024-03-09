package com.example.clickracer.game

import com.example.clickracer.game.data.repositories.SessionsRepositoryImplementation
import com.example.clickracer.game.domain.repositories.SessionsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GameModule {
    @Provides
    fun provideSessionsRepository() : SessionsRepository {
        return SessionsRepositoryImplementation()
    }
}
