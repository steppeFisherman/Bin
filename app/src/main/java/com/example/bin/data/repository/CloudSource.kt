package com.example.bin.data.repository

import com.example.bin.data.model.mappers.MapCacheToDomain
import com.example.bin.data.model.mappers.MapCloudToCache
import com.example.bin.data.net.CloudData
import com.example.bin.data.room.AppRoomDao
import com.example.bin.domain.model.ResultBin
import javax.inject.Inject

interface CloudSource {

    suspend fun fetchCloud(binNumber: String): ResultBin

    class Base @Inject constructor(
        private val cloudData: CloudData,
        private val appDao: AppRoomDao,
        private val mapperCloudToCache: MapCloudToCache,
        private val mapperCacheToDomain: MapCacheToDomain,
    ) : CloudSource {

        override suspend fun fetchCloud(binNumber: String): ResultBin {
            val cloud = cloudData.fetchCloud(binNumber = binNumber)
            val cache = mapperCloudToCache.transform(cloud, binNumber)
            val domain = mapperCacheToDomain.transform(cache)
            appDao.insertBin(cache)
            return ResultBin.Success(domain)
        }
    }
}