package com.example.clickracer.auth

import com.example.clickracer.auth.data.repositories.AuthRepositoryImplementation
import com.example.clickracer.auth.domain.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Provides
    fun provideAuthAuthRepository(): AuthRepository {
        return AuthRepositoryImplementation()
    }
}
