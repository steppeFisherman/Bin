package com.example.bin.ui.di

import com.example.bin.domain.FetchUseCase
import com.example.bin.domain.Repository
import com.example.bin.ui.model.MapDomainToUi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideFetchUseCase(repository: Repository): FetchUseCase =
        FetchUseCase.Base(repository = repository)

    @Provides
    fun provideMapDomainToUi(): MapDomainToUi = MapDomainToUi.Base()
}