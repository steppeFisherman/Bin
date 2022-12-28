package com.example.bin.ui.screens

import androidx.lifecycle.*
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
class HistoryFragmentViewModel @Inject constructor(
    private val fetchUseCase: FetchUseCase,
    private val mapper: MapDomainToUi
) : ViewModel() {

    private var mBins = MutableLiveData<List<DataUi>>()
    private var mError = MutableLiveData<ErrorType>()

    val bins: LiveData<List<DataUi>>
        get() = mBins
    val error: LiveData<ErrorType>
        get() = mError

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    private fun fetchData() {
        viewModelScope.launch(exceptionHandler) {
            when (val result = fetchUseCase.fetchCached()) {
                is ResultBin.SuccessBins -> {
                    mBins = result.bins.map { list ->
                        list.map { dataDomain ->
                            mapper.mapDomainToUi(dataDomain)
                        }
                    } as MutableLiveData<List<DataUi>>
                }
                is ResultBin.Fail -> mError.value = result.errorType
                else -> {}
            }
        }
    }

    init {
        fetchData()
    }
}