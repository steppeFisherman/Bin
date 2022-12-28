package com.example.bin.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bin.domain.FetchUseCase
import com.example.bin.domain.model.ErrorType
import com.example.bin.domain.model.ResultBin
import com.example.bin.ui.model.DataUi
import com.example.bin.ui.model.MapDomainToUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val fetchUseCase: FetchUseCase,
    private val mapper: MapDomainToUi
) : ViewModel() {

    private var mBin = MutableLiveData<DataUi>()
    private var mError = MutableLiveData<ErrorType>()
    private var mLoading = MutableLiveData<Boolean>()

    val bin: LiveData<DataUi>
        get() = mBin
    val error: LiveData<ErrorType>
        get() = mError
    val loading: LiveData<Boolean>
        get() = mLoading

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    fun fetch(binNumber: String) {
        mLoading.value = true
        viewModelScope.launch(exceptionHandler) {
            when (val result = fetchUseCase.fetchCloud(binNumber)) {
                is ResultBin.Success -> {
                    mLoading.value = false
                    mBin.value = mapper.mapDomainToUi(result.bin)
                }
                is ResultBin.Fail -> {
                    mLoading.value = false
                    mError.value = result.errorType
                }
                else -> {}
            }
        }
    }
}