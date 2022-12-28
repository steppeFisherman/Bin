package com.example.bin.data.net

import com.example.bin.data.model.DataCloud
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface Service {

    companion object {
        const val BASE_URL = "https://lookup.binlist.net/"
        private const val HEADER = "Accept-Version: 3"
    }

    @Headers(HEADER)
    @GET("{bin}")
    suspend fun fetchCloud(
        @Path("bin") binNumber: String
    ): DataCloud
}

