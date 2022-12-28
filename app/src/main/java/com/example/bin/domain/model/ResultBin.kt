package com.example.bin.domain.model

import androidx.lifecycle.LiveData

sealed class ResultBin {
    data class Success(val bin: DataDomain) : ResultBin()
    data class SuccessBins(val bins: LiveData<List<DataDomain>>) : ResultBin()
    data class Fail(val errorType: ErrorType) : ResultBin()
}
