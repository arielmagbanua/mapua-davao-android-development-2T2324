package com.example.clickracer

import android.app.Application
import com.example.clickracer.auth.data.repositories.AuthRepositoryImplementation
import com.example.clickracer.auth.domain.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent

@HiltAndroidApp
class ClickRacerApp : Application() {
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideAuthAuthRepository(): AuthRepository {
        return AuthRepositoryImplementation()
    }
}
