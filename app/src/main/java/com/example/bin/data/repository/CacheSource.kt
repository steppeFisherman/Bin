package com.example.bin.data.repository

import androidx.lifecycle.map
import com.example.bin.data.model.mappers.MapCacheToDomain
import com.example.bin.data.room.AppRoomDao
import com.example.bin.domain.model.ResultBin
import javax.inject.Inject

interface CacheSource {

    fun fetchCached(): ResultBin

    class Base @Inject constructor(
        private val appDao: AppRoomDao,
        private val mapperCacheToDomain: MapCacheToDomain,
        private val exceptionHandle: ExceptionHandle
    ) : CacheSource {

        override fun fetchCached(): ResultBin = try {
            val cache = appDao.fetchAllBins()
            val domain = cache.map { listDataCache ->
                listDataCache.map {
                    mapperCacheToDomain.transform(it)
                }
            }
            ResultBin.SuccessBins(domain)
        } catch (e: Exception) {
            exceptionHandle.handle(exception = e)
        }
    }
}