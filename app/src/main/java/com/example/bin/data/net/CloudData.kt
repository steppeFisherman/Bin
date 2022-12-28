package com.example.bin.data.net

import com.example.bin.data.model.DataCloud
import javax.inject.Inject

interface CloudData {

    suspend fun fetchCloud(binNumber: String): DataCloud

    class Base @Inject constructor(private val service: Service) : CloudData {
        override suspend fun fetchCloud(binNumber: String): DataCloud =
            service.fetchCloud(binNumber = binNumber)
    }
}

