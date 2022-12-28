package com.example.bin.data.repository

import com.example.bin.domain.Repository
import com.example.bin.domain.model.ResultBin

class RepositoryImpl(
    private val cloudSource: CloudSource,
    private val cacheSource: CacheSource,
    private val exceptionHandle: ExceptionHandle,
) : Repository {

    override val binsCached: ResultBin
        get() = try {
            cacheSource.fetchCached()
        } catch (e: Exception) {
            exceptionHandle.handle(exception = e)
        }

    override suspend fun fetchCloud(binNumber: String): ResultBin = try {
        cloudSource.fetchCloud(binNumber)
    } catch (e: Exception) {
        exceptionHandle.handle(exception = e)
    }
}