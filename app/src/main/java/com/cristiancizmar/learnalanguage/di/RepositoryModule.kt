package com.cristiancizmar.learnalanguage.di

import com.cristiancizmar.learnalanguage.data.FileWordsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFileWordsRepository() = FileWordsRepository()

}