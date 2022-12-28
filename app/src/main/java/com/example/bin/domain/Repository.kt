package com.example.bin.domain

import com.example.bin.domain.model.ResultBin

interface Repository {
    val binsCached: ResultBin
    suspend fun fetchCloud(binNumber: String): ResultBin
}