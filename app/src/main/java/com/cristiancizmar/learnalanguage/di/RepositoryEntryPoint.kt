package com.cristiancizmar.learnalanguage.di

import com.cristiancizmar.learnalanguage.data.FileWordsRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface RepositoryEntryPoint {
    
    fun getFileWordsRepository(): FileWordsRepository
}