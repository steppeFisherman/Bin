package com.example.bin.domain

import com.example.bin.domain.model.ResultBin
import javax.inject.Inject

interface FetchUseCase {

    fun fetchCached(): ResultBin
    suspend fun fetchCloud(binNumber: String): ResultBin

    class Base @Inject constructor(private val repository: Repository) : FetchUseCase {
        override fun fetchCached(): ResultBin = repository.binsCached
        override suspend fun fetchCloud(binNumber: String) = repository.fetchCloud(binNumber)
    }
}