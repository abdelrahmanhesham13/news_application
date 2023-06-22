package com.example.newsapplication.di

import com.example.data.repository.NewRepositoryImp
import com.example.data.repository.RemoteDataSource
import com.example.domain.repository.NewRepository
import com.example.remote.source.RemoteDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSourceImp: RemoteDataSourceImp): RemoteDataSource

    @Binds
    @ViewModelScoped
    abstract fun provideMovieRepository(repository : NewRepositoryImp) : NewRepository

}